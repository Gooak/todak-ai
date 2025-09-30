package com.example.today_diary_ai.domain.repository

interface GeminiRepository {
    suspend fun getGeminiAnswer(prompt: String): String
}