package com.example.today_diary_ai.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.today_diary_ai.data.local.entity.DiaryEntity
import com.example.today_diary_ai.enum.MoodType
import com.example.today_diary_ai.enum.WeatherType
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

data class DiaryModel(
    val diaryId : Int = 0,
    val diaryDate: LocalDate,
    val diaryTitle : String,
    val diaryContent : String,
    val diaryAiContent : String,
    val diaryWeather : WeatherType,
    val diaryMood : MoodType,
    var isExpanded : MutableState<Boolean> = mutableStateOf(false),
) {

    fun toEntity(): DiaryEntity {
        return DiaryEntity(
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
