package com.balancemania.api.auth.application

import com.balancemania.api.auth.application.oauth.KakaoOAuthService
import com.balancemania.api.auth.model.response.OAuthLoginLinkResponse
import com.balancemania.api.auth.model.response.OAuthTokenResponse
import com.balancemania.api.user.domain.vo.OAuthProvider
import com.balancemania.api.user.domain.vo.OauthInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class OAuthService(
    private val kakaoOAuthService: KakaoOAuthService,
) {
    private val logger = KotlinLogging.logger { }

    /** oauth withdraw 페이지용 login link 가져오기 */
    suspend fun getOAuthWithdrawLoginLink(provider: OAuthProvider, uri: String): OAuthLoginLinkResponse {
        return when (provider) {
            OAuthProvider.KAKAO -> kakaoOAuthService.getOAuthWithdrawLoginLink(uri)
        }
    }

    /** oauth token 가져오기 */
    suspend fun getOAuthWithdrawToken(
        provider: OAuthProvider,
        code: String,
    ): OAuthTokenResponse {
        return when (provider) {
            OAuthProvider.KAKAO -> kakaoOAuthService.getOAuthWithdrawToken(code)
        }
    }

    /** oauth info 가져오기 */
    suspend fun getOAuthInfo(
        provider: OAuthProvider,
        accessToken: String,
    ): OauthInfo {
        return when (provider) {
            OAuthProvider.KAKAO -> kakaoOAuthService.getOAuthInfo(accessToken)
        }.oauthInfo
    }

    /** oauth 유저 회원 탈퇴하기 */
    suspend fun withdraw(oauthInfo: OauthInfo) {
        when (oauthInfo.oAuthProvider) {
            OAuthProvider.KAKAO -> kakaoOAuthService.withdraw(oauthInfo.oAuthId)
        }
    }
}
