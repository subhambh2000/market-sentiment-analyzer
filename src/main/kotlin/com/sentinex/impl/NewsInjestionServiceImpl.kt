package com.sentinex.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.genai.types.GenerateContentConfig
import com.sentinex.model.NewsArticle
import com.sentinex.service.AIClientService
import com.sentinex.service.NewsInjestionService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class NewsInjestionServiceImpl(
    private val newsApiClient: NewsApiClient,
    @Qualifier("AIClientService") private val aIClientService: AIClientService,
) : NewsInjestionService {
    final val mapper = ObjectMapper()

    override fun getNewsArticles(query: String): List<NewsArticle> {
        val response = newsApiClient.fetchArticles(query)

        val articles = response.map {
            it.map { newsDTO ->
                newsDTO.tickers.addAll(
                    extractTickerFromArticle(newsDTO.description, newsDTO.content)
                )
                newsDTO.toEntity()
            }
        }

        return articles.block() ?: emptyList()
    }

    override fun extractTickerFromArticle(description: String?, content: String): List<String> {
        val promptTemplate = ClassPathResource("prompts/ticker-extraction-prompt.txt")
            .inputStream.bufferedReader().use { it.readText() }

        val config = GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .candidateCount(1)
            .build()

        val finalPrompt = promptTemplate
            .replace("{{CONTENT}}", content)
            .replace("{{DESCRIPTION}}", description ?: "Not Available")

        val response = aIClientService.getAIResponse(finalPrompt, config) ?: return emptyList()
        return mapper.readValue(response.text(), object : TypeReference<List<String>>() {})
    }
}