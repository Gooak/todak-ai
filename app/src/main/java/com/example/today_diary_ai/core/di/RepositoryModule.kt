package com.example.today_diary_ai.core.di

import com.example.today_diary_ai.data.repository.DiaryRepositoryImpl
import com.example.today_diary_ai.data.repository.GeminiRepositoryImpl
import com.example.today_diary_ai.domain.repository.DiaryRepository
import com.example.today_diary_ai.domain.repository.GeminiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDiaryRepository(
        impl: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    abstract fun bindGeminiRepository(
        impl: GeminiRepositoryImpl
    ): GeminiRepository
}