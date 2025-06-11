package com.sentinex.dto

import java.time.LocalDateTime

data class NewsArticleApiDTO(
    val source: SourceDTO,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: LocalDateTime,
    val content: String,
) {
    fun toNewsDTO(): NewsDTO {
        return NewsDTO(
            title = this.title,
            description = this.description,
            content = this.content,
            publishedAt = this.publishedAt,
            source = this.source.name,
            url = this.url
        )
    }
}

data class SourceDTO(
    val name: String
)
