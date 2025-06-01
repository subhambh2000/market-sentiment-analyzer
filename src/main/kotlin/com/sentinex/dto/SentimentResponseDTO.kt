package com.sentinex.dto

import java.time.LocalDate

data class SentimentResponseDTO(
    val ticker: String,
    val averageSentiment: Double,
    val dailySentiment: List<DailySentiment>
)

data class DailySentiment(
    val date: LocalDate,
    val score: Double
)
