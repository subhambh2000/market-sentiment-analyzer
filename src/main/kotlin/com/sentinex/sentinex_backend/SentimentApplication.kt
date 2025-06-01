package com.sentinex.sentinex_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SentimentApplication

fun main(args: Array<String>) {
	runApplication<SentimentApplication>(*args)
}
