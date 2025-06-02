package com.sentinex.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "aggregated_sentiment")
data class AggregatedSentiment(
    @Id @GeneratedValue val id: UUID? = null,
    var ticker: String,
    var date: LocalDate,
    var avgSentiment: Double,
    var articleCount: Int
    )
