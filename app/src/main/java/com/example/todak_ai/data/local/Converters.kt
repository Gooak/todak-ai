package com.example.todak_ai.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.todak_ai.domain.enum.MoodType
import com.example.todak_ai.domain.enum.WeatherType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ProvidedTypeConverter
class Converters {

    // --- LocalDate 변환 ---
    // LocalDate -> String
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE) // "YYYY-MM-DD" 형식으로 저장
    }

    // String -> LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }
    }

    // --- WeatherType 변환 ---
    // WeatherType -> String
    @TypeConverter
    fun fromWeatherType(weather: WeatherType?): String? {
        return weather?.name // enum의 이름을 String으로 저장
    }

    // String -> WeatherType
    @TypeConverter
    fun toWeatherType(weatherName: String?): WeatherType? {
        return weatherName?.let { WeatherType.valueOf(it) } // String을 다시 enum으로 변환
    }

    // --- MoodType 변환 ---
    // MoodType -> String
    @TypeConverter
    fun fromMoodType(mood: MoodType?): String? {
        return mood?.name
    }

    // String -> MoodType
    @TypeConverter
    fun toMoodType(moodName: String?): MoodType? {
        return moodName?.let { MoodType.valueOf(it) }
    }
}