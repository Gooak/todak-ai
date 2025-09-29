package com.example.today_diary_ai.presentation.ui.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.today_diary_ai.presentation.viewmodel.SettingViewModel

@Composable
fun SettingScreen() {
    val settingViewModel: SettingViewModel = hiltViewModel()

    val soundMeasurementCycle : Float by settingViewModel.soundMeasurementCycle.collectAsState()
    val soundRecordingLength : Float by settingViewModel.soundRecordingLength.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("측정 주기 (${soundMeasurementCycle.toInt()}분)")
        Slider(
            value = soundMeasurementCycle,
            onValueChange = {
                settingViewModel.modifySoundMeasurementCycle(it)
            },
            valueRange = 1f..10f,
            steps = 9
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("녹음 길이 (${soundRecordingLength.toInt()}초)")
        Slider(
            value = soundRecordingLength,
            onValueChange = {
                settingViewModel.modifySoundRecordingLength(it)
            },
            valueRange = 1f..60f,
            steps = 59
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

