package com.example.today_diary_ai.presentation.navigation

import com.example.today_diary_ai.R


enum class BottomMenus(
    val label: String,
    val route: String,
    val icon: Int,
    val selectedIcon: Int,
) {
    DIARY(
        "일기",
        "diary",
        R.drawable.outline_calendar_today_24,
        R.drawable.baseline_calendar_today_24,
    ),
    STATISTICS(
        "통계",
        "statistics",
        R.drawable.outline_pie_chart_24,
        R.drawable.baseline_pie_chart_24,
    ),
}