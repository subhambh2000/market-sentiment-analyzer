package com.sentinex.repository

import com.sentinex.model.NewsArticle
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NewsArticleRepository : JpaRepository<NewsArticle, UUID> {
}