package com.sentinex.impl

import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GenerateContentResponse
import com.sentinex.service.AIClientService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AIClientServiceImpl : AIClientService {
    @Value("\${api.key}")
    private lateinit var apiKey: String

    @Value("\${model}")
    private lateinit var model: String

    override fun getAIResponse(prompt: String, config: GenerateContentConfig?): GenerateContentResponse? {
        val client = Client.builder().apiKey(apiKey).build()

        val response = client.models.generateContent(
            model,
            prompt,
            config
        )
        return response
    }
}