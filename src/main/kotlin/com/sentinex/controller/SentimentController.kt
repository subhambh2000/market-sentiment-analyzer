package com.sentinex.controller

import com.sentinex.dto.KeywordSummaryResponse
import com.sentinex.dto.SentimentResponseDTO
import com.sentinex.service.SentimentService
import org.springframework.web.bind.annotation.*

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
}