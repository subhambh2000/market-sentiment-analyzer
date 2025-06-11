package com.sentinex.dto

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticleApiDTO>
)
