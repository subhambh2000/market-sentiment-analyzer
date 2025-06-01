package com.sentinex.service

import com.sentinex.dto.KeywordSummaryResponse
import com.sentinex.dto.SentimentResponseDTO
import com.sentinex.model.NewsArticle
import com.sentinex.model.SentimentAnalysis

interface SentimentService {
    fun analyze(article: NewsArticle): SentimentAnalysis
    fun getTickerSentimentTrend(ticker: String): SentimentResponseDTO
    fun summarizeByKeyword(keyword: String): KeywordSummaryResponse
}
