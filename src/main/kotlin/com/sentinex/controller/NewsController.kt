package com.sentinex.controller

import com.sentinex.dto.NewsDTO
import com.sentinex.model.NewsArticle
import com.sentinex.model.SentimentAnalysis
import com.sentinex.repository.NewsArticleRepository
import com.sentinex.service.NewsInjestionService
import com.sentinex.service.SentimentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/news")
class NewsController(
    private val newsRepo: NewsArticleRepository,
    private val sentimentService: SentimentService,
    private val newsInjestionService: NewsInjestionService
) {
    @PostMapping("/ingest")
    fun ingestNews(@RequestBody dto: NewsDTO): NewsArticle {
        val article = dto.toEntity()

        val savedArticle = newsRepo.save(article)
        sentimentService.analyze(savedArticle)

        return savedArticle
    }

    @PostMapping("/news")
    fun fetchAndAnalyzeNews(@RequestBody query: String): ResponseEntity<List<SentimentAnalysis>> {
        val articles = newsInjestionService.getNewsArticles(query)

        val sentimentAnalysis = articles.map {
            newsRepo.save(it)
            sentimentService.analyze(it)
        }
        return ResponseEntity.ok(sentimentAnalysis)
    }

}