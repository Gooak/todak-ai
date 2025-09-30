package com.example.todak_ai.core.di

import android.content.Context
import androidx.room.Room
import com.example.todak_ai.data.local.AppDatabase
import com.example.todak_ai.data.local.Converters
import com.example.todak_ai.data.local.dao.DiaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideConverters(): Converters {
        return Converters()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context, converters: Converters
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "today_diary_ai_database"
        )
            .addTypeConverter(converters)
            .build()
    }

    @Provides
    fun provideSoundLogDao(db: AppDatabase): DiaryDao {
        return db.diaryDao()
    }
}