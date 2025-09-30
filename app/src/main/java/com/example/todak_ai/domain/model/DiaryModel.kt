package com.example.todak_ai.domain.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.todak_ai.data.local.entity.DiaryEntity
import com.example.todak_ai.domain.enum.MoodType
import com.example.todak_ai.domain.enum.WeatherType
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
