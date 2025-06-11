package com.sentinex.repository

import com.sentinex.model.AggregatedSentiment
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface AggregatedSentimentRepository : JpaRepository<AggregatedSentiment, UUID> {
    fun findByTickerOrderByDateAsc(ticker: String): List<AggregatedSentiment>
    fun findByDate(date: LocalDate): List<AggregatedSentiment>
    fun findByTickerAndDate(ticker: String, date: LocalDate): AggregatedSentiment?
}