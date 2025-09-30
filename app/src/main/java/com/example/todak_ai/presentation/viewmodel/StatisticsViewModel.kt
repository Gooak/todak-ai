package com.example.todak_ai.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todak_ai.domain.usecase.DiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    diaryUseCase: DiaryUseCase // Hilt를 통해 UseCase 주입
) : ViewModel() {

    // 모든 다이어리 리스트
    private val allDiariesFlow = diaryUseCase.getAllDiary

    // --- 기분 통계 데이터 ---
    // 기분 데이터를 (타입, 개수) 형태로 가공하고 개수 순으로 정렬
    private val moodCounts = allDiariesFlow.map { diaries ->
        diaries.groupingBy { it.diaryMood }.eachCount()
            .entries.sortedByDescending { it.value }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // CircularChart에 전달할 데이터 리스트 (Float)
    val moodData: StateFlow<List<Float>> = moodCounts.map { counts ->
        counts.map { it.value.toFloat() }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // CircularChart에 전달할 색상 리스트 (Color)
    val moodColors: StateFlow<List<Color>> = moodCounts.map { counts ->
        counts.map { it.key.color } // Enum에 정의된 color 사용
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 범례를 위한 데이터
    val moodLegend: StateFlow<List<Triple<Color, String, Int>>> = moodCounts.map { counts ->
        counts.map { Triple(it.key.color, it.key.description, it.value) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- 날씨 통계 데이터 (위와 동일한 방식) ---
    private val weatherCounts = allDiariesFlow.map { diaries ->
        diaries.groupingBy { it.diaryWeather }.eachCount()
            .entries.sortedByDescending { it.value }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // CircularChart에 전달할 데이터 리스트 (Float)
    val weatherData: StateFlow<List<Float>> = weatherCounts.map { it.map { entry -> entry.value.toFloat() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // CircularChart에 전달할 색상 리스트 (Color)
    val weatherColors: StateFlow<List<Color>> = weatherCounts.map { it.map { entry -> entry.key.color } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 범례를 위한 데이터
    val weatherLegend: StateFlow<List<Triple<Color, String, Int>>> = weatherCounts.map { counts ->
        counts.map { Triple(it.key.color, it.key.description, it.value) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 총 다이어리 개수
    val totalDiaryCount: StateFlow<Int> = allDiariesFlow.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}