package com.example.today_diary_ai.domain.repository
import com.example.today_diary_ai.domain.model.SettingModel
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val getSetting: Flow<List<SettingModel>>
    suspend fun settingUpsert(
        soundMeasurementCycle: Float,
        soundRecordingLength: Float
    )
}
