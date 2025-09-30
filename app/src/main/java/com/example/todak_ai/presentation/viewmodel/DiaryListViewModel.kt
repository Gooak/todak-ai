package com.example.todak_ai.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todak_ai.domain.model.DiaryModel
import com.example.todak_ai.domain.usecase.DiaryUseCase
import com.example.todak_ai.domain.usecase.GeminiUseCase
import com.example.todak_ai.domain.enum.MoodType
import com.example.todak_ai.domain.enum.WeatherType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel
class DiaryListViewModel @Inject constructor(
    private val diaryUseCase: DiaryUseCase
) : ViewModel() {

    // 내 다이어리 리스트
    private val _diaryList = MutableStateFlow<List<DiaryModel>>(emptyList())
    val diaryList: MutableStateFlow<List<DiaryModel>> = _diaryList

    init {
        viewModelScope.launch {
            diaryUseCase.getAllDiary.collect {
                _diaryList.value = it
            }
        }
    }

    // 일기 삭제
    fun deleteDiary(diary: DiaryModel) {
        val currentList = _diaryList.value.toMutableList()
        currentList.remove(diary)
        _diaryList.value = currentList

        viewModelScope.launch {
            diaryUseCase.deleteDiary(diary)
        }
    }
}
