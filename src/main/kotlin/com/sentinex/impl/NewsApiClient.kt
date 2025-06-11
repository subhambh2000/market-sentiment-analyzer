package com.sentinex.impl

import com.sentinex.dto.NewsApiResponse
import com.sentinex.dto.NewsDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class NewsApiClient(
    @Value("\${news.api.key}") val newsApiKey: String,
    @Value("\${news.api.base.url}") val url: String,
) {
    fun fetchArticles(query: String): Mono<List<NewsDTO>> {
        val response = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", newsApiKey)
            .build()
            .get()
            .uri("?q=${query}")
            .retrieve()
            .bodyToMono(NewsApiResponse::class.java)
        return response.map { apiRes -> apiRes.articles.map { it.toNewsDTO() } }
    }
}