package com.example.today_diary_ai.data.repository

import com.example.today_diary_ai.data.local.dao.SettingDao
import com.example.today_diary_ai.data.local.entity.SettingEntity
import com.example.today_diary_ai.domain.model.SettingModel
import com.example.today_diary_ai.domain.repository.SettingRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val dao: SettingDao
) : SettingRepository {

    override val getSetting: Flow<List<SettingModel>> =
        dao.getSetting().map { list -> list.map { it.toDomain() } }

    override suspend fun settingUpsert(soundMeasurementCycle: Float, soundRecordingLength: Float) {
        dao.settingUpsert(
            SettingEntity(
                soundMeasurementCycleSetting = soundMeasurementCycle,
                soundRecordingLengthSetting = soundRecordingLength
            )
        )
    }
}
