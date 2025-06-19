package com.sentinex.service

import com.sentinex.model.NewsArticle

interface NewsInjestionService {
    fun getNewsArticles(query: String): List<NewsArticle>
    fun extractTickerFromArticle(description: String?, content: String): List<String>
}