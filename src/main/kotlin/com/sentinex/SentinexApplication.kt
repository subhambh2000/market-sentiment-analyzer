package com.sentinex

import com.sentinex.config.DotEnvInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication

@SpringBootApplication
class SentimentApplication

fun main(args: Array<String>) {
    runApplication<SentimentApplication>(*args) {
        addInitializers(DotEnvInitializer())
    }
}