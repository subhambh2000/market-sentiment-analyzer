package com.sentinex.model

import com.sentinex.dto.NewsDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "news_articles")
data class NewsArticle(
    @Id @GeneratedValue val id: UUID? = null,
    val title: String,
    @Column(columnDefinition = "TEXT") val content: String,
    val source: String,
    val tickers: String, // comma-separated
    val publishedAt: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
