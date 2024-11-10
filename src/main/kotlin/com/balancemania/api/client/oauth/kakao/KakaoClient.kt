package com.balancemania.api.client.oauth.kakao

import com.balancemania.api.client.oauth.kakao.model.KakaoOAuthTokenResponse
import com.balancemania.api.client.oauth.kakao.model.KakaoOAuthUserInfoResponse
import com.balancemania.api.client.oauth.kakao.model.KakaoOAuthWithdrawResponse
import com.balancemania.api.common.BEARER
import com.balancemania.api.common.KAKAO_AK
import com.balancemania.api.config.auth.OAuthUrlConfig
import com.balancemania.api.extension.awaitWithMdc
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

interface KakaoClient {
    fun getToken(
        redirectUrl: String,
        code: String,
        clientId: String,
        clientSecret: String,
    ): KakaoOAuthTokenResponse

    fun getUserInfo(accessToken: String): KakaoOAuthUserInfoResponse

    fun withdraw(targetId: String, adminKey: String): KakaoOAuthWithdrawResponse?

    class Default(
        private val webClient: WebClient,
        private val kakaoOAuthUrlConfig: OAuthUrlConfig.KakaoOAuthUrlConfig,
    ) : KakaoClient {
        private val logger = KotlinLogging.logger { }

        override fun getToken(
            redirectUrl: String,
            code: String,
            clientId: String,
            clientSecret: String,
        ): KakaoOAuthTokenResponse {
            val url = kakaoOAuthUrlConfig.kauthUrl + String.format(
                kakaoOAuthUrlConfig.tokenUrl,
                clientId,
                redirectUrl,
                code,
                clientSecret
            )
            return webClient.post()
                .uri(url)
                .retrieve()
                .bodyToMono(KakaoOAuthTokenResponse::class.java)
                .awaitWithMdc()
        }

        override fun getUserInfo(
            accessToken: String,
        ): KakaoOAuthUserInfoResponse {
            return webClient.get()
                .uri(kakaoOAuthUrlConfig.kapiUrl + kakaoOAuthUrlConfig.userInfoUrl)
                .header("Authorization", BEARER + accessToken)
                .retrieve()
                .bodyToMono(KakaoOAuthUserInfoResponse::class.java)
                .awaitWithMdc()
        }

        override fun withdraw(targetId: String, adminKey: String): KakaoOAuthWithdrawResponse? {
            val multiValueMap = LinkedMultiValueMap<String, String>().apply {
                setAll(
                    mapOf(
                        "target_id_type" to "user_id",
                        "target_id" to targetId
                    )
                )
            }
            return webClient.post()
                .uri(kakaoOAuthUrlConfig.kapiUrl + kakaoOAuthUrlConfig.unlinkUrl)
                .header("Authorization", KAKAO_AK + adminKey)
                .body(BodyInserters.fromFormData(multiValueMap))
                .retrieve()
                .bodyToMono(KakaoOAuthWithdrawResponse::class.java)
                .awaitWithMdc()
        }
    }
}
