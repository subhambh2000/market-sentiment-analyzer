package com.sentinex.controller

import com.sentinex.dto.KeywordSummaryResponse
import com.sentinex.dto.SentimentResponseDTO
import com.sentinex.dto.TickerSentimentDTO
import com.sentinex.service.SentimentService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("api/v1/sentiment")
class SentimentController(private val sentimentService: SentimentService) {
    @GetMapping("/ticker/{symbol}")
    fun getSentimentBySymbol(@PathVariable symbol: String): SentimentResponseDTO {
        return sentimentService.getTickerSentimentTrend(symbol)
    }

    @GetMapping("/summary")
    fun getSummaryByKeyword(@RequestParam keyword: String): KeywordSummaryResponse {
        return sentimentService.summarizeByKeyword(keyword)
    }

    @GetMapping("/top")
    fun getTopSentimentByKeyword(
        @RequestParam("sort", defaultValue = "positive") sort: String,
        @RequestParam("limit", defaultValue = "5") limit: Int,
        @RequestParam("date", required = false) date: String?
    ): List<TickerSentimentDTO> {
        val parsedDate = if (date != null) LocalDate.parse(date) else LocalDate.now()
        return sentimentService.getTopSentimentTickers(sort, limit, parsedDate)
    }
}