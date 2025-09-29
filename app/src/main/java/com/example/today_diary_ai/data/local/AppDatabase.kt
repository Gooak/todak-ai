package com.example.today_diary_ai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.today_diary_ai.data.local.dao.SettingDao
import com.example.today_diary_ai.data.local.dao.DiaryDao
import com.example.today_diary_ai.data.local.entity.SettingEntity
import com.example.today_diary_ai.data.local.entity.DiaryEntity

@Database(entities = [SettingEntity::class, DiaryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingDao(): SettingDao
    abstract fun diaryDao(): DiaryDao
}