package com.example.today_diary_ai.domain.usecase

import com.example.today_diary_ai.domain.model.SettingModel
import com.example.today_diary_ai.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingUseCase(
    private val repository: SettingRepository
) {
    val getSetting: Flow<List<SettingModel>> = repository.getSetting

    suspend fun updateSetting(
        soundMeasurementCycle: Float,
        soundRecordingLength: Float
    ) {
        repository.settingUpsert(soundMeasurementCycle, soundRecordingLength)
    }
}
