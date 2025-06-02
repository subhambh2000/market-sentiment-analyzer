package com.sentinex.service

import com.sentinex.dto.KeywordSummaryResponse
import com.sentinex.dto.SentimentResponseDTO
import com.sentinex.dto.TickerSentimentDTO
import com.sentinex.model.NewsArticle
import com.sentinex.model.SentimentAnalysis
import java.time.LocalDate

interface SentimentService {
    fun analyze(article: NewsArticle): SentimentAnalysis
    fun getTickerSentimentTrend(ticker: String): SentimentResponseDTO
    fun summarizeByKeyword(keyword: String): KeywordSummaryResponse
    fun getTopSentimentTickers(sort: String, limit: Int, date: LocalDate): List<TickerSentimentDTO>
}
