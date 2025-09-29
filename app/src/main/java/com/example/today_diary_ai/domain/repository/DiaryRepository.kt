package com.example.today_diary_ai.domain.repository

import com.example.today_diary_ai.domain.model.DiaryModel
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    val getAllDiary: Flow<List<DiaryModel>>
    suspend fun insertDiary(diary: DiaryModel)

    suspend fun deleteDiary(diary: DiaryModel)
}
