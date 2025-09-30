package com.example.today_diary_ai.enum

import androidx.compose.ui.graphics.Color
import com.example.today_diary_ai.R

enum class WeatherType(val description: String, val iconRes: Int, val color: Color) {
    SUNNY("맑음", R.drawable.ic_weather_sunny, Color(0xFFFFF8E1)),
    CLOUDY("흐림", R.drawable.ic_weather_cloudy, Color(0xFFD3D3D3)),
    RAINY("비", R.drawable.ic_weather_rainy, Color(0xFFB0C4DE)),
    SNOWY("눈", R.drawable.ic_weather_snowy, Color(0xFFF0F8FF)),
    THUNDER("바람", R.drawable.ic_weather_thunder, Color(0xFFC9D9C9));



    companion object {
        fun fromDescription(description: String): WeatherType? =
            entries.firstOrNull { it.description == description }
    }
}
