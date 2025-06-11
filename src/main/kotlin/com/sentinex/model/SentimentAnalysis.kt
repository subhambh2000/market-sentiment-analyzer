package com.sentinex.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "sentiment_analysis")
data class SentimentAnalysis(
    @Id @GeneratedValue val id: UUID? = null,
    @ManyToOne @JoinColumn(name = "article_id") val article: NewsArticle,
    @Column(columnDefinition = "TEXT") val summary: String,
    val sentiment: Double,
    val emotion: String,
    val analyzedAt: LocalDateTime = LocalDateTime.now()
    )
