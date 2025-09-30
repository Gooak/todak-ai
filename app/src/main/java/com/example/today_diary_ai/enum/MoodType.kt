package com.example.today_diary_ai.enum

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.today_diary_ai.R

// 기분 종류를 정의하는 Enum Class
enum class MoodType(val description : String, val iconRes: Int, val color : Color) {
    HAPPY("행복", R.drawable.ic_mood_happy, Color(0xFFB9F2D2)),
    GOOD("기쁨", R.drawable.ic_mood_good, Color(0xFFFFB347)),
    NEUTRAL("보통", R.drawable.ic_mood_neutral, Color(0xFFD3CFC1)),
    SAD("속상", R.drawable.ic_mood_sad, Color(0xFF779ECB)),
    BAD("나쁨", R.drawable.ic_moo_bad, Color(0xFFFF6961));


    companion object {
        fun fromDescription(description: String): MoodType? =
            entries.firstOrNull { it.description == description }
    }
}