package com.example.today_diary_ai.domain.usecase

import com.example.today_diary_ai.domain.repository.GeminiRepository

class GeminiUseCase(
    private val repository: GeminiRepository
) {
    suspend fun getGeminiAnswer(prompt: String): String {
        return repository.getGeminiAnswer(prompt)
    }
}