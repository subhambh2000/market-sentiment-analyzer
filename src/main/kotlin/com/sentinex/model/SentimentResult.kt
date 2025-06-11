package com.sentinex.model

data class SentimentResult(
    val summary: String,
    val sentiment: Double,
    val emotion: String
)
