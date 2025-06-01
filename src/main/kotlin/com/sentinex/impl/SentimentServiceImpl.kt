package com.sentinex.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GenerateContentResponse
import com.sentinex.dto.DailySentiment
import com.sentinex.dto.KeywordSummaryResponse
import com.sentinex.dto.SentimentResponseDTO
import com.sentinex.model.NewsArticle
import com.sentinex.model.SentimentAnalysis
import com.sentinex.model.SentimentResult
import com.sentinex.repository.SentimentAnalysisRepository
import com.sentinex.service.SentimentService
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SentimentServiceImpl(private val sentimentAnalysisRepository: SentimentAnalysisRepository) : SentimentService {

    @Value("\${api.key}")
    private lateinit var apiKey: String

    @Value("\${model}")
    private lateinit var model: String

    val objectMapper = ObjectMapper()

    override fun analyze(article: NewsArticle): SentimentAnalysis {
        val promptTemplate = ClassPathResource("prompts/sentiment-prompt.txt")
            .inputStream.bufferedReader().use { it.readText() }

        val config = GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .candidateCount(1)
            .build()

        val finalPrompt = promptTemplate.replace("{{ARTICLE_CONTENT}}", article.content)

        val response = getAIResponse(finalPrompt, config)

        val result = objectMapper.readValue(response?.text(), SentimentResult::class.java)

        val sentimentAnalysis = SentimentAnalysis(
            article = article,
            summary = result.summary,
            sentiment = result.sentiment,
            emotion = result.emotion,
        )

        return sentimentAnalysisRepository.save(sentimentAnalysis)
    }

    override fun getTickerSentimentTrend(ticker: String): SentimentResponseDTO {
        val results = sentimentAnalysisRepository.findByTickerLike(ticker)
        val groupedResults = results.groupBy { it.analyzedAt.toLocalDate() }
        val dailySentiment = groupedResults.map { (date, entries) ->
            val avgScore = entries.map { it.sentiment }.average()
            DailySentiment(date, avgScore)
        }.sortedByDescending { it.date }

        val averageSentiment = results.map { it.sentiment }.average()

        return SentimentResponseDTO(
            ticker,
            averageSentiment,
            dailySentiment
        )
    }

    override fun summarizeByKeyword(keyword: String): KeywordSummaryResponse {
        val promptTemplate = ClassPathResource("prompts/summary-prompt.txt")
            .inputStream.bufferedReader().use { it.readText() }

        val summaryResult = sentimentAnalysisRepository.findByKeywordInArticle(keyword)
        if (summaryResult.isEmpty()) {
            return KeywordSummaryResponse(keyword, LocalDate.now(), "No such relevant articles found", 0.0)
        }

        val avg = summaryResult.map { it.sentiment }.average()

        val topContent = summaryResult.sortedByDescending { it.sentiment }
            .take(3)
            .joinToString("\n\n") { it.article.content }

        val summaryPrompt = promptTemplate.replace("{{keyword}}", keyword)
            .replace("{{topContent}}", topContent)

        val summaryResponse = getAIResponse(summaryPrompt)?.text() ?: "Not relevant summary generated"

        return KeywordSummaryResponse(
            keyword = keyword,
            date = LocalDate.now(),
            summary = summaryResponse,
            avgScore = avg
        )

    }

    private fun getAIResponse(prompt: String, config: GenerateContentConfig? = null): GenerateContentResponse? {
        val client = Client.builder().apiKey(apiKey).build()

        val response = client.models.generateContent(
            model,
            prompt,
            config
        )
        return response
    }
}