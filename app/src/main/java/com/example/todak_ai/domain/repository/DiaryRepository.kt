package com.example.todak_ai.domain.repository

import com.example.todak_ai.domain.model.DiaryModel
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    val getAllDiary: Flow<List<DiaryModel>>
    suspend fun insertDiary(diary: DiaryModel)

    suspend fun deleteDiary(diary: DiaryModel)
}
