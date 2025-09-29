package com.example.today_diary_ai.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.today_diary_ai.domain.usecase.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
) : ViewModel(){
    private val _soundMeasurementCycle = MutableStateFlow<Float>(5f)
    val soundMeasurementCycle : StateFlow<Float> = _soundMeasurementCycle

    private val _soundRecordingLength = MutableStateFlow<Float>(60f)
    val soundRecordingLength : StateFlow<Float> = _soundRecordingLength


    init {
        viewModelScope.launch {
            Log.d("SettingViewModel", "init")
            settingUseCase.getSetting
                .collect { settings ->
                    Log.d("SettingViewModel", "Settings: $settings")
                    settings.firstOrNull()?.let {
                        _soundMeasurementCycle.value = it.soundMeasurementCycle
                        _soundRecordingLength.value = it.soundRecordingLength
                    }
                }
        }
    }

    //측정 주기 수정
    fun modifySoundMeasurementCycle(value : Float){
        _soundMeasurementCycle.value = value
        viewModelScope.launch {
            settingUseCase.updateSetting(_soundMeasurementCycle.value, _soundRecordingLength.value)
        }
    }

    //녹음 길이 수정
    fun modifySoundRecordingLength(value : Float){
        _soundRecordingLength.value = value
        viewModelScope.launch {
            settingUseCase.updateSetting(_soundMeasurementCycle.value, _soundRecordingLength.value)
        }
    }
}