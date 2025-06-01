package com.sentinex.sentiment_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SentimentApplication

fun main(args: Array<String>) {
	runApplication<SentimentApplication>(*args)
}
