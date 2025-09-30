package com.example.todak_ai.data.repository

import com.example.todak_ai.BuildConfig
import com.example.todak_ai.domain.repository.GeminiRepository
import com.google.ai.client.generativeai.GenerativeModel // ⬅️ 이 import가 가능해져야 합니다.
import jakarta.inject.Inject

class GeminiRepositoryImpl @Inject constructor() : GeminiRepository {
    override suspend fun getGeminiAnswer(prompt: String): String {
        return try {
            val generativeModel = GenerativeModel(
                modelName = "gemini-2.5-flash",
                apiKey = BuildConfig.GEMINI_API_KEY
            )
            val response = generativeModel.generateContent(prompt)
            response.text ?: "AI 답변을 생성하는 데 실패했습니다."
        } catch (e: Exception) {
            // 예외 처리 (e.g., 네트워크 오류, API 키 오류 등)
            e.printStackTrace()
            "오류가 발생했습니다: ${e.message}"
        }
    }
}