package com.balancemania.api.client.config

import com.balancemania.api.client.WebClientFactory
import com.balancemania.api.client.mediapipe.MediaPipeClient
import com.balancemania.api.client.mediapipe.SuspendableMediaPipeClient
import com.balancemania.api.client.oauth.kakao.KakaoClient
import com.balancemania.api.client.oauth.kakao.SuspendableKakaoClient
import com.balancemania.api.config.MediaPipeConfig
import com.balancemania.api.config.auth.OAuthUrlConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MediaPipeClientConfig(
    private val mediaPipeConfig: MediaPipeConfig
) {
    private val logger = KotlinLogging.logger {}

    @Bean
    fun mediaPipeClient(): MediaPipeClient {
        val webClient = WebClientFactory.generateWithoutBaseUrl()
        logger.info { "initialized mediaPipe client" }
        return SuspendableMediaPipeClient(webClient, mediaPipeConfig)
    }
}
