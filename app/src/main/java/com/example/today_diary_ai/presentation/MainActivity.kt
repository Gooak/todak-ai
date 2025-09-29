package com.example.today_diary_ai.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.today_diary_ai.presentation.ui.screens.MainScreen
import com.example.today_diary_ai.presentation.ui.theme.TodayDiaryAiTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayDiaryAiTheme {
                MainScreen()
            }
        }
    }
}