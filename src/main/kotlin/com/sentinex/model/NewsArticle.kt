package com.sentinex.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "news_articles")
data class NewsArticle(
    @Id @GeneratedValue val id: UUID? = null,
    val title: String,
    val description: String?,
    @Column(columnDefinition = "TEXT") val content: String,
    val source: String,
    val url: String,
    @ElementCollection
    @CollectionTable(name = "news_article_tickers", joinColumns = [JoinColumn(name = "article_id")])
    @Column(name = "ticker")
    val tickers: List<String>,
    val publishedAt: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
