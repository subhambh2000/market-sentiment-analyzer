package com.sentinex.dto

import com.sentinex.model.NewsArticle
import java.time.LocalDateTime

data class NewsDTO(
    val title: String,
    val description: String?,
    val content: String,
    val publishedAt: LocalDateTime,
    val source: String,
    val url: String,
    val tickers: MutableList<String> = mutableListOf()
) {
    fun toEntity(): NewsArticle {
        return NewsArticle(
            title = title,
            description = description,
            content = content,
            source = source,
            url = url,
            tickers = tickers,
            publishedAt = publishedAt
        )
    }
}
