package com.sentinex.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class DotEnvInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load()

        dotenv.entries().forEach { entry ->
            System.setProperty(entry.key, entry.value)
        }
    }
}