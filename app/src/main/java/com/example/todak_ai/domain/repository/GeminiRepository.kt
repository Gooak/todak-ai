package com.example.todak_ai.domain.repository

interface GeminiRepository {
    suspend fun getGeminiAnswer(prompt: String): String
}