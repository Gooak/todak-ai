package com.example.today_diary_ai.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.today_diary_ai.domain.model.DiaryModel
import com.example.today_diary_ai.domain.usecase.DiaryUseCase
import com.example.today_diary_ai.domain.usecase.GeminiUseCase
import com.example.today_diary_ai.enum.MoodType
import com.example.today_diary_ai.enum.WeatherType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryUseCase: DiaryUseCase,
    private val geminiUseCase: GeminiUseCase
) : ViewModel() {

    // 내 다이어리 리스트
    private val _diaryList = MutableStateFlow<List<DiaryModel>>(emptyList())
    val diaryList: MutableStateFlow<List<DiaryModel>> = _diaryList

    // 제목
    private val _titleText = MutableStateFlow<String>("")
    val titleText: MutableStateFlow<String> = _titleText

    // 내용
    private val _contentText = MutableStateFlow<String>("")
    val contentText: MutableStateFlow<String> = _contentText

    // AI 답변을 저장할 변수
    private val _geminiResponse = MutableStateFlow<String>("")
    val geminiResponse: StateFlow<String> = _geminiResponse

    // 로딩
    private val _isLoadingAi = MutableStateFlow(false)
    val isLoadingAi: StateFlow<Boolean> = _isLoadingAi

    init {
        viewModelScope.launch {
            diaryUseCase.getAllDiary.collect {
                _diaryList.value = it
            }
        }
    }

    // 제목 변경
    fun onTitleChange(newTitle: String) {
        _titleText.value = newTitle
    }

    // 내용 변경
    fun onContentChange(newContent: String) {
        _contentText.value = newContent
    }

    // 일기 추가
    fun insertDiary(
        diaryDate: LocalDate,
        diaryAiContent: String,
        diaryWeather: WeatherType,
        diaryMood: MoodType
    ) {
        viewModelScope.launch {
            val diary = DiaryModel(
                diaryDate = diaryDate,
                diaryTitle = _titleText.value,
                diaryContent = _contentText.value,
                diaryAiContent = diaryAiContent,
                diaryWeather = diaryWeather,
                diaryMood = diaryMood,
            )
            diaryUseCase.insertDiary(diary)
        }
    }

    // 일기 삭제
    fun deleteDiary(diary: DiaryModel) {
        val currentList = _diaryList.value.toMutableList()
        currentList.remove(diary)
        _diaryList.value = currentList

        viewModelScope.launch {
            diaryUseCase.deleteDiary(diary)
        }
    }

    // 제미나이 ai 답변 넣기
    fun requestAiAnswer(currentWeather: WeatherType, currentMood: MoodType) {
        val currentTitle = _titleText.value
        val currentContent = _contentText.value

        if (currentTitle.isBlank() || currentContent.isBlank()) {
            _geminiResponse.value = "제목, 내용, 기분을 모두 선택해주세요."
            return
        }

        viewModelScope.launch {
            _isLoadingAi.value = true
            _geminiResponse.value = ""

            val prompt = """
                너는 사용자의 일기에 따뜻하게 공감해주는 훌륭한 친구야.
                아래 주어진 정보를 바탕으로, 따뜻하고 다정하며 진심으로 공감하는 짧은 답변을 1~2 문장으로 작성해줘.
                답변은 반드시 한국어로 해주고, 이모티콘을 1~2개 정도 사용해서 친근함을 표현해줘.

                ---
                [오늘의 날씨]: "${currentWeather.description}"
                [오늘의 기분]: "${currentMood.description}"
                [일기 제목]: "$currentTitle"
                [일기 내용]: "$currentContent"
                ---
            """.trimIndent()

            val answer = geminiUseCase.getGeminiAnswer(prompt)

            _geminiResponse.value = answer
            _isLoadingAi.value = false
        }
    }
}
