package com.example.today_diary_ai.util

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.log10
import kotlin.math.sqrt

object AudioUtils {
    private const val TAG = "AudioUtils"
    private const val DEFAULT_SAMPLE_RATE = 44100
    private const val DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
    private const val DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    private var audioRecord: AudioRecord? = null
    private var fileOutputStream: FileOutputStream? = null
    private var recordingJob: Job? = null
    private var isCurrentlyRecording = false

    private lateinit var currentOutputFile: File

    // 사용자에게 친숙한 dB SPL 값 (평균, 최대, 최소)
    val decibelFlow = MutableSharedFlow<Triple<Float, Float, Float>>(replay = 1)

    fun isRecording() = isCurrentlyRecording

    fun createAudioFile(context: Context, fileName: String): File {
        val audioDir = File(context.filesDir, "audio")
        if (!audioDir.exists()) audioDir.mkdirs()
        return File(audioDir, fileName)
    }

    @SuppressLint("MissingPermission")
    fun startRecording(
        context: Context,
        outputFile: File,
        coroutineScope: CoroutineScope,
        sampleRate: Int = DEFAULT_SAMPLE_RATE,
        channelConfig: Int = DEFAULT_CHANNEL_CONFIG,
        audioFormat: Int = DEFAULT_AUDIO_FORMAT
    ): Boolean {
        if (isCurrentlyRecording) return false
        currentOutputFile = outputFile

        try {
            val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
            if (minBufferSize == AudioRecord.ERROR_BAD_VALUE) return false

            audioRecord =
                AudioRecord.Builder()
                    .setAudioSource(MediaRecorder.AudioSource.MIC)
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(audioFormat)
                            .setSampleRate(sampleRate)
                            .setChannelMask(channelConfig)
                            .build()
                    )
                    .setBufferSizeInBytes(minBufferSize * 2)
                    .build()

            if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) return false

            fileOutputStream = FileOutputStream(outputFile)
            audioRecord?.startRecording()
            isCurrentlyRecording = true

            recordingJob = coroutineScope.launch(Dispatchers.IO) {
                val buffer = ShortArray(minBufferSize)
                val decibelList = mutableListOf<Float>()

                try {
                    while (isActive && isCurrentlyRecording) {
                        val read = audioRecord?.read(buffer, 0, buffer.size) ?: -1
                        if (read > 0) {
                            // 파일 저장
                            val byteBuffer = java.nio.ByteBuffer.allocate(read * 2)
                            buffer.take(read).forEach { byteBuffer.putShort(it) }
                            fileOutputStream?.write(byteBuffer.array())

                            // 🔹 수정된 dB SPL 계산
                            val db = calculateDbSpl(buffer, read)
                            decibelList.add(db)

                            val avg = decibelList.average().toFloat()
                            val max = decibelList.maxOrNull() ?: db
                            val min = decibelList.minOrNull() ?: db

                            CoroutineScope(Dispatchers.Default).launch {
                                decibelFlow.emit(Triple(avg, max, min))
                            }
                        }
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "IOException during recording: ${e.message}", e)
                }
            }

            return true
        } catch (e: Exception) {
            Log.e(TAG, "startRecording exception: ${e.message}")
            isCurrentlyRecording = false
            return false
        }
    }

    // 🔹 수정된 dB SPL 계산 로직
    private fun calculateDbSpl(buffer: ShortArray, read: Int): Float {
        if (read <= 0) return 0f

        var sum = 0.0
        for (i in 0 until read) {
            sum += (buffer[i] * buffer[i]).toDouble()
        }
        val rms = sqrt(sum / read)

        // 16비트 오디오 기준 정규화 (최대값 32767)
        val dbSpl = 20 * log10(rms / Short.MAX_VALUE)

        // 로그 확인용
        Log.d(TAG, "RMS=$rms, dB=$dbSpl")

        // 🎯 coerceIn 제거 → 원시값 그대로 리턴
        return dbSpl.toFloat()
    }

    fun stopRecording(): File? {
        if (!isCurrentlyRecording) return null
        isCurrentlyRecording = false

        try {
            audioRecord?.stop()
            audioRecord?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping AudioRecord: ${e.message}")
        }
        audioRecord = null

        try {
            fileOutputStream?.flush()
            fileOutputStream?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing FileOutputStream: ${e.message}")
        }
        fileOutputStream = null

        recordingJob?.cancel()
        recordingJob = null

        Log.d(TAG, "Recording stopped. File saved: ${currentOutputFile.absolutePath}")
        return currentOutputFile
    }
}
