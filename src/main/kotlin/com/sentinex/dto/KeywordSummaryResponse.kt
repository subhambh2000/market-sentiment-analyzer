package com.sentinex.dto

import java.time.LocalDate

data class KeywordSummaryResponse(
    val keyword: String,
    val date: LocalDate,
    val summary: String,
    val avgScore: Double
)
