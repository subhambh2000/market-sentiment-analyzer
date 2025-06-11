package com.sentinex.dto

data class TickerSentimentDTO(
    val ticker: String,
    val averageSentiment: Double,
    val articleCount: Int
)
