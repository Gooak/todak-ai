package com.example.today_diary_ai.core.di

import com.example.today_diary_ai.data.repository.SettingRepositoryImpl
import com.example.today_diary_ai.data.repository.DiaryRepositoryImpl
import com.example.today_diary_ai.domain.repository.SettingRepository
import com.example.today_diary_ai.domain.repository.DiaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSettingRepository(
        impl: SettingRepositoryImpl
    ): SettingRepository

    @Binds
    abstract fun bindDiaryRepository(
        impl: DiaryRepositoryImpl
    ): DiaryRepository
}