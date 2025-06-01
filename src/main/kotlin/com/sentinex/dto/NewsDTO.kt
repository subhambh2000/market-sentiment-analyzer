package com.sentinex.dto

import com.sentinex.model.NewsArticle
import java.time.LocalDateTime

data class NewsDTO(
    val title: String,
    val content: String,
    val source: String,
    val tickers: List<String>,
    val publishedAt: LocalDateTime,
) {
    fun toEntity() : NewsArticle {
        return NewsArticle(
            title = title,
            content = content,
            source = source,
            tickers = tickers.joinToString(","),
            publishedAt = publishedAt
        )
    }
}
