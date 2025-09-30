package com.example.todak_ai.core.di

import com.example.todak_ai.domain.repository.DiaryRepository
import com.example.todak_ai.domain.repository.GeminiRepository
import com.example.todak_ai.domain.usecase.DiaryUseCase
import com.example.todak_ai.domain.usecase.GeminiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideDiaryUseCase(repository: DiaryRepository): DiaryUseCase {
        return DiaryUseCase(repository)
    }

    @Provides
    fun provideGeminiUseCase(repository: GeminiRepository): GeminiUseCase {
        return GeminiUseCase(repository)
    }
}