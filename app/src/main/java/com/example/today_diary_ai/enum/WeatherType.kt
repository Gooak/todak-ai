package com.example.today_diary_ai.enum

import com.example.today_diary_ai.R

enum class WeatherType(val iconRes: Int) {
    SUNNY(R.drawable.ic_weather_sunny),
    CLOUDY(R.drawable.ic_weather_cloudy),
    RAINY(R.drawable.ic_weather_rainy),
    SNOWY(R.drawable.ic_weather_snowy),
    THUNDER(R.drawable.ic_weather_thunder)
}