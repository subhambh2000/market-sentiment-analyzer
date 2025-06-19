package com.sentinex.service

import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GenerateContentResponse

interface AIClientService {
    fun getAIResponse(prompt: String, config: GenerateContentConfig? = null): GenerateContentResponse?
}