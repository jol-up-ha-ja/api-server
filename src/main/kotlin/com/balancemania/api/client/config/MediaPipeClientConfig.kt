package com.balancemania.api.client.config

import com.balancemania.api.client.WebClientFactory
import com.balancemania.api.client.mediapipe.MediaPipeClient
import com.balancemania.api.config.MediaPipeConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MediaPipeClientConfig(
    private val mediaPipeConfig: MediaPipeConfig,
) {
    private val logger = KotlinLogging.logger {}

    @Bean
    fun mediaPipeClient(): MediaPipeClient {
        val webClient = WebClientFactory.generateWithoutBaseUrl(
            connectionTimeoutMillis = 1000 * 10,
            readTimeoutMillis = 1000 * 10,
            writeTimeoutMillis = 1000 * 10
        )
        logger.info { "initialized mediaPipe client" }
        return MediaPipeClient.Default(webClient, mediaPipeConfig)
    }
}
