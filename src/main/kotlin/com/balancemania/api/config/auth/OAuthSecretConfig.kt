package com.balancemania.api.config.auth

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.reflect.full.declaredMemberProperties

@Configuration
@EnableConfigurationProperties(
    OAuthSecretConfig.KakaoOAuthSecretConfig::class
)
class OAuthSecretConfig(
    val kakaoOAuthSecretConfig: KakaoOAuthSecretConfig,
) {
    init {
        val logger = KotlinLogging.logger { }
        OAuthSecretConfig::class.declaredMemberProperties
            .forEach { config ->
                logger.info { config.get(this).toString() }
            }
    }

    @ConfigurationProperties(prefix = "oauth.kakao")
    class KakaoOAuthSecretConfig(
        val clientId: String,
        val clientSecret: String,
        val adminKey: String,
    )
}
