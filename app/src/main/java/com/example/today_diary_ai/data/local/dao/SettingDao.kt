package com.example.today_diary_ai.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.today_diary_ai.data.local.entity.SettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {
    @Query("SELECT * FROM setting_table")
    fun getSetting(): Flow<List<SettingEntity>>

    @Upsert
    suspend fun settingUpsert(detail: SettingEntity)
}