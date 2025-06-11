package com.sentinex.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.genai.types.GenerateContentConfig
import com.sentinex.dto.DailySentiment
import com.sentinex.dto.KeywordSummaryResponse
import com.sentinex.dto.SentimentResponseDTO
import com.sentinex.dto.TickerSentimentDTO
import com.sentinex.model.AggregatedSentiment
import com.sentinex.model.NewsArticle
import com.sentinex.model.SentimentAnalysis
import com.sentinex.model.SentimentResult
import com.sentinex.repository.AggregatedSentimentRepository
import com.sentinex.repository.SentimentAnalysisRepository
import com.sentinex.service.AIClientService
import com.sentinex.service.SentimentService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SentimentServiceImpl(
    private val sentimentAnalysisRepository: SentimentAnalysisRepository,
    private val aggregatedSentimentRepository: AggregatedSentimentRepository,
    @Qualifier("AIClientService") private val aIClientService: AIClientService
) : SentimentService {

    val objectMapper = ObjectMapper()

    override fun analyze(article: NewsArticle): SentimentAnalysis {
        val promptTemplate = ClassPathResource("prompts/sentiment-prompt.txt")
            .inputStream.bufferedReader().use { it.readText() }

        val config = GenerateContentConfig.builder()
            .responseMimeType("application/json")
            .candidateCount(1)
            .build()

        val finalPrompt = promptTemplate.replace("{{ARTICLE_CONTENT}}", article.content)

        val response = aIClientService.getAIResponse(finalPrompt, config)

        val result = objectMapper.readValue(response?.text(), SentimentResult::class.java)

        val sentimentAnalysis = SentimentAnalysis(
            article = article,
            summary = result.summary,
            sentiment = result.sentiment,
            emotion = result.emotion,
        )
        val analysis = sentimentAnalysisRepository.save(sentimentAnalysis)

        updateAggregateSentiments(analysis)

        return analysis
    }

    override fun getTopSentimentTickers(
        sort: String,
        limit: Int,
        date: LocalDate
    ): List<TickerSentimentDTO> {
        val aggregates = aggregatedSentimentRepository.findByDate(date)

        val sortedAgg = when (sort) {
            "positive" -> aggregates.sortedByDescending { it.avgSentiment }
            else -> aggregates.sortedBy { it.avgSentiment }
        }

        return sortedAgg.take(limit).map {
            TickerSentimentDTO(
                it.ticker,
                it.avgSentiment,
                it.articleCount
            )
        }
    }

    override fun getTickerSentimentTrend(ticker: String): SentimentResponseDTO {
        val results = sentimentAnalysisRepository.findByTicker(ticker)
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

        val summaryResponse = aIClientService.getAIResponse(summaryPrompt)?.text() ?: "Not relevant summary generated"

        return KeywordSummaryResponse(
            keyword = keyword,
            date = LocalDate.now(),
            summary = summaryResponse,
            avgScore = avg
        )

    }

    private fun updateAggregateSentiments(analysis: SentimentAnalysis) {
        val analyzedDate = analysis.analyzedAt.toLocalDate()

        analysis.article.tickers.forEach {
            val existing = aggregatedSentimentRepository.findByTickerAndDate(it, analyzedDate)

            if (existing != null) {
                val totalSentiment = ((existing.avgSentiment * existing.articleCount) + analysis.sentiment)
                val newArticleCount = existing.articleCount + 1
                val newAvg = totalSentiment / newArticleCount

                existing.avgSentiment = newAvg
                existing.articleCount = newArticleCount

                aggregatedSentimentRepository.save(existing)
            } else {
                val newAgg = AggregatedSentiment(
                    ticker = it,
                    avgSentiment = analysis.sentiment,
                    date = analyzedDate,
                    articleCount = 1
                )
                aggregatedSentimentRepository.save(newAgg)
            }
        }
    }
}
