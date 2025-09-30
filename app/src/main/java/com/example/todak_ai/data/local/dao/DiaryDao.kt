package com.example.todak_ai.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todak_ai.data.local.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * FROM DiaryEntity ORDER BY diaryId DESC")
    fun getAllDiary(): Flow<List<DiaryEntity>>

    @Insert
    suspend fun insertDiary(diary: DiaryEntity)

    @Delete
    suspend fun deleteDiary(diary: DiaryEntity)
}
