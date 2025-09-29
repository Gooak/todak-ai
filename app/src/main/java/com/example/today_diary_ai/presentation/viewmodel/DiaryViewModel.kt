package com.example.today_diary_ai.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.today_diary_ai.domain.model.DiaryModel
import com.example.today_diary_ai.domain.usecase.DiaryUseCase
import com.example.today_diary_ai.enum.MoodType
import com.example.today_diary_ai.enum.WeatherType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryUseCase: DiaryUseCase,
) : ViewModel() {

    private val _diaryList = MutableStateFlow<List<DiaryModel>>(emptyList())
    val diaryList : MutableStateFlow<List<DiaryModel>> = _diaryList

    private val _titleText = MutableStateFlow<String>("")
    val titleText : MutableStateFlow<String> = _titleText

    private val _contentText = MutableStateFlow<String>("")
    val contentText : MutableStateFlow<String> = _contentText

    init {
        viewModelScope.launch {
            diaryUseCase.getAllDiary.collect {
                _diaryList.value = it
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _titleText.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _contentText.value = newContent
    }

    // 일기 추가
    fun insertDiary(diaryDate : LocalDate, diaryAiContent : String, diaryWeather : WeatherType, diaryMood : MoodType){
        viewModelScope.launch {
            val diary = DiaryModel(
                diaryDate = diaryDate,
                diaryTitle = _titleText.value,
                diaryContent = _contentText.value,
                diaryAiContent = diaryAiContent,
                diaryWeather = diaryWeather,
                diaryMood = diaryMood,
            )
            diaryUseCase.insertDiary(diary)
        }
    }

    //일기 삭제
    fun deleteDiary(diary : DiaryModel){
        val currentList = _diaryList.value.toMutableList()
        currentList.remove(diary)
        _diaryList.value = currentList

        viewModelScope.launch {
            diaryUseCase.deleteDiary(diary)
        }
    }
}
