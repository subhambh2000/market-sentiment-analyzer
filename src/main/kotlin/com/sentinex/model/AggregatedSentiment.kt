package com.sentinex.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "sentiment_analysis")
data class AggregatedSentiment(
    @Id @GeneratedValue val id: UUID? = null,
    val ticker: String,
    val date: LocalDateTime,
    val avgSentiment: Float,
    val articleCount: Int
    )
