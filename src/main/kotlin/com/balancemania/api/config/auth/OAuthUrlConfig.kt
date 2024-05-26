package com.balancemania.api.config.auth

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.reflect.full.declaredMemberProperties

@Configuration
@EnableConfigurationProperties(
    OAuthUrlConfig.KakaoOAuthUrlConfig::class
)
class OAuthUrlConfig(
    val kakaoOAuthUrlConfig: KakaoOAuthUrlConfig,
) {
    init {
        val logger = KotlinLogging.logger { }
        OAuthUrlConfig::class.declaredMemberProperties
            .forEach { config ->
                logger.info { config.get(this).toString() }
            }
    }

    @ConfigurationProperties(prefix = "oauth-url.kakao")
    class KakaoOAuthUrlConfig(
        val withdrawCallbackUrl: String,
        val unlinkUrl: String,
        val userInfoUrl: String,
        val authorizeUrl: String,
        val tokenUrl: String,
        val kauthUrl: String,
        val kapiUrl: String,
        val redirectUrl: String,
    )
}
