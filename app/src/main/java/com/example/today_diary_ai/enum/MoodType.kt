package com.example.today_diary_ai.enum

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.today_diary_ai.R

// 기분 종류를 정의하는 Enum Class
enum class MoodType(val iconRes: Int, val description: String) {
    HAPPY(R.drawable.ic_mood_happy, "행복해요"),
    GOOD(R.drawable.ic_mood_good, "좋아요"),
    NEUTRAL(R.drawable.ic_mood_neutral, "그냥 그래요"),
    SAD(R.drawable.ic_mood_sad, "속상해요"),
    BAD(R.drawable.ic_moo_bad, "나빠요")
}