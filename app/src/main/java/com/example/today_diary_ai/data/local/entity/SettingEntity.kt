package com.example.today_diary_ai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.today_diary_ai.domain.model.SettingModel

@Entity(tableName = "setting_table")
data class SettingEntity(
    @PrimaryKey() val id: Int = 1,
    val soundMeasurementCycleSetting: Float,
    val soundRecordingLengthSetting: Float
){
    fun toDomain(): SettingModel =
        SettingModel(soundMeasurementCycle = soundMeasurementCycleSetting,
            soundRecordingLength = soundRecordingLengthSetting)
}