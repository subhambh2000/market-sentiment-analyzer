package com.sentinex.service

import com.sentinex.model.NewsArticle
import org.springframework.stereotype.Service

@Service
interface NewsInjestionService {
    fun getNewsArticles(query: String): List<NewsArticle>
    fun extractTickerFromArticle(description: String?, content: String): List<String>
}