package com.example.today_diary_ai.core.di

import com.example.today_diary_ai.domain.repository.SettingRepository
import com.example.today_diary_ai.domain.repository.DiaryRepository
import com.example.today_diary_ai.domain.usecase.SettingUseCase
import com.example.today_diary_ai.domain.usecase.DiaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSettingUseCase(repository: SettingRepository): SettingUseCase {
        return SettingUseCase(repository)
    }

    @Provides
    fun provideSoundUseCase(repository: DiaryRepository): DiaryUseCase {
        return DiaryUseCase(repository)
    }
}