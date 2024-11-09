package com.balancemania.api.auth.application.oauth

import com.balancemania.api.auth.model.OAuthUserInfoDto
import com.balancemania.api.auth.model.response.OAuthLoginLinkResponse
import com.balancemania.api.auth.model.response.OAuthTokenResponse
import com.balancemania.api.client.oauth.kakao.KakaoClient
import com.balancemania.api.config.auth.OAuthSecretConfig
import com.balancemania.api.config.auth.OAuthUrlConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KakaoOAuthService(
    val kakaoOAuthSecretConfig: OAuthSecretConfig.KakaoOAuthSecretConfig,
    val kakaoOAuthUrlConfig: OAuthUrlConfig.KakaoOAuthUrlConfig,
    val kakaoClient: KakaoClient,
    @Value("\${server.domain-name}")
    private val domainName: String,
) {
    private val logger = KotlinLogging.logger { }

    /** link */
    fun getOAuthLoginLinkDev(): OAuthLoginLinkResponse {
        val redirectUrl = domainName + kakaoOAuthUrlConfig.redirectUrl
        return OAuthLoginLinkResponse(
            kakaoOAuthUrlConfig.kauthUrl +
                String.format(
                    kakaoOAuthUrlConfig.authorizeUrl,
                    kakaoOAuthSecretConfig.clientId,
                    redirectUrl
                )
        )
    }

    fun getOAuthWithdrawLoginLink(uri: String): OAuthLoginLinkResponse {
        val redirectUrl = domainName + kakaoOAuthUrlConfig.withdrawCallbackUrl
        return OAuthLoginLinkResponse(
            kakaoOAuthUrlConfig.kauthUrl +
                String.format(
                    kakaoOAuthUrlConfig.authorizeUrl,
                    kakaoOAuthSecretConfig.clientId,
                    redirectUrl
                )
        )
    }

    /** oauth token 받아오기 */
    fun getOAuthTokenDev(code: String): OAuthTokenResponse {
        val redirectUrl = domainName + kakaoOAuthUrlConfig.redirectUrl
        return getKakaoToken(redirectUrl, code)
    }

    fun getOAuthWithdrawToken(code: String): OAuthTokenResponse {
        val redirectUrl = domainName + kakaoOAuthUrlConfig.withdrawCallbackUrl
        return getKakaoToken(redirectUrl, code)
    }

    private fun getKakaoToken(redirectUrl: String, code: String): OAuthTokenResponse {
        return kakaoClient.getToken(
            redirectUrl = redirectUrl,
            code = code,
            clientId = kakaoOAuthSecretConfig.clientId,
            clientSecret = kakaoOAuthSecretConfig.clientSecret
        ).run { OAuthTokenResponse.fromKakao(this) }
    }

    /** 유저 정보를 가져옵니다. */
    fun getOAuthInfo(accessToken: String): OAuthUserInfoDto {
        return kakaoClient.getUserInfo(accessToken)
            .run { OAuthUserInfoDto.fromKakao(this) }
    }

    /** 회원 탈퇴합니다 */
    fun withdraw(oAuthId: String) {
        kakaoClient.withdraw(targetId = oAuthId, adminKey = kakaoOAuthSecretConfig.adminKey)
    }
}
