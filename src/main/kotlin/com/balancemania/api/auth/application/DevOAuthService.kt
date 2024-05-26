package com.balancemania.api.auth.application

import com.balancemania.api.auth.application.oauth.KakaoOAuthService
import com.balancemania.api.auth.model.response.OAuthLoginLinkResponse
import com.balancemania.api.auth.model.response.OAuthTokenResponse
import com.balancemania.api.user.domain.vo.OAuthProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class DevOAuthService(
    private val kakaoOAuthService: KakaoOAuthService,
) {
    private val logger = KotlinLogging.logger { }

    /** oauth login link 가져오기 */
    suspend fun getOAuthLoginLinkDev(provider: OAuthProvider): OAuthLoginLinkResponse {
        return when (provider) {
            OAuthProvider.KAKAO -> kakaoOAuthService.getOAuthLoginLinkDev()
        }
    }

    /** oauth token 가져오기 */
    suspend fun getOAuthTokenDev(provider: OAuthProvider, code: String): OAuthTokenResponse {
        return when (provider) {
            OAuthProvider.KAKAO -> kakaoOAuthService.getOAuthTokenDev(code)
        }
    }
}
