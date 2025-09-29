package com.example.today_diary_ai.domain.usecase

import com.example.today_diary_ai.domain.model.DiaryModel
import com.example.today_diary_ai.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow

class DiaryUseCase(
    private val repository: DiaryRepository
) {
    val getAllDiary : Flow<List<DiaryModel>> = repository.getAllDiary

    suspend fun insertDiary(
        diary : DiaryModel
    ){
        repository.insertDiary(diary)
    }
    suspend fun deleteDiary(
        diary : DiaryModel
    ){
        repository.deleteDiary(diary)
    }
}
