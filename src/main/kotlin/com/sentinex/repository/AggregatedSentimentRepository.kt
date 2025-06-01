package com.sentinex.repository

import com.sentinex.model.AggregatedSentiment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AggregatedSentimentRepository : JpaRepository<AggregatedSentiment, UUID> {
    fun findByTickerOrderByDateAsc(ticker: String): List<AggregatedSentiment>
}