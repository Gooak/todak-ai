package com.example.todak_ai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todak_ai.data.local.dao.DiaryDao
import com.example.todak_ai.data.local.entity.DiaryEntity

@Database(entities = [DiaryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}