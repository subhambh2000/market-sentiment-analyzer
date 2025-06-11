package com.sentinex.service

import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GenerateContentResponse
import org.springframework.stereotype.Service

@Service
interface AIClientService {
    fun getAIResponse(prompt: String, config: GenerateContentConfig? = null): GenerateContentResponse?
}