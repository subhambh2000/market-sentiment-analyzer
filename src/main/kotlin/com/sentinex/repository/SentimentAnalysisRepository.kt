package com.sentinex.repository

import com.sentinex.model.SentimentAnalysis
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*


interface SentimentAnalysisRepository : JpaRepository<SentimentAnalysis, UUID> {
    @Query("SELECT s from SentimentAnalysis s where CONCAT(',',s.article.tickers,',') like CONCAT('%,',:ticker,',%')")
    fun findByTickerLike(@Param("ticker") ticker: String): List<SentimentAnalysis>

    @Query("""SELECT s from SentimentAnalysis s where (LOWER(s.article.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR 
        LOWER(s.article.title) LIKE LOWER(CONCAT('%', :keyword, '%')) )""")
    fun findByKeywordInArticle(@Param("keyword") keyword: String): List<SentimentAnalysis>
}