package com.balancemania.api.client.config

import com.balancemania.api.client.WebClientFactory
import com.balancemania.api.client.oauth.kakao.KakaoClient
import com.balancemania.api.config.auth.OAuthUrlConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OAuthClientConfig(
    private val kakaoOAuthUrlConfig: OAuthUrlConfig.KakaoOAuthUrlConfig,
) {
    private val logger = KotlinLogging.logger {}

    @Bean
    fun kakaoClient(): KakaoClient {
        val webClient = WebClientFactory.generateWithoutBaseUrl()
        logger.info { "initialized oauth kakao client" }
        return KakaoClient.Default(webClient, kakaoOAuthUrlConfig)
    }
}
