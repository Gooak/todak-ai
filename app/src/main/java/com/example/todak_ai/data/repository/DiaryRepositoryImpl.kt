package com.example.todak_ai.data.repository

import com.example.todak_ai.data.local.dao.DiaryDao
import com.example.todak_ai.domain.model.DiaryModel
import com.example.todak_ai.domain.repository.DiaryRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DiaryRepositoryImpl @Inject constructor(
    private val dao: DiaryDao
) : DiaryRepository {

    override val getAllDiary: Flow<List<DiaryModel>> =
        dao.getAllDiary().map { list -> list.map { it.toDomain() } }

    override suspend fun insertDiary(diary: DiaryModel) {
        dao.insertDiary(diary.toEntity())
    }

    override suspend fun deleteDiary(diary: DiaryModel) {
        dao.deleteDiary(diary.toEntity())
    }
}
