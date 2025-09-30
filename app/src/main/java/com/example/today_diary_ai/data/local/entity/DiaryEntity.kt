package com.example.today_diary_ai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.today_diary_ai.domain.model.DiaryModel
import com.example.today_diary_ai.domain.enum.MoodType
import com.example.today_diary_ai.domain.enum.WeatherType
import java.time.LocalDate

@Entity
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val diaryId: Int = 0,
    val diaryDate: LocalDate,
    val diaryTitle : String,
    val diaryContent : String,
    val diaryAiContent : String,
    val diaryWeather : WeatherType,
    val diaryMood : MoodType,
) {
    fun toDomain(): DiaryModel {
        return DiaryModel(
            diaryId = diaryId,
            diaryDate = diaryDate,
            diaryTitle = diaryTitle,
            diaryContent = diaryContent,
            diaryAiContent = diaryAiContent,
            diaryWeather = diaryWeather,
            diaryMood = diaryMood,
        )
    }
}
