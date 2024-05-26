package com.balancemania.api.auth.model.response

import com.balancemania.api.client.oauth.kakao.model.KakaoOAuthTokenResponse

data class OAuthTokenResponse(
    /** oauth access token */
    val accessToken: String,
    /** oauth refresh token */
    val refreshToken: String,
) {
    companion object {
        fun fromKakao(kakaoOAuthTokenResponse: KakaoOAuthTokenResponse): OAuthTokenResponse {
            return OAuthTokenResponse(
                accessToken = kakaoOAuthTokenResponse.accessToken,
                refreshToken = kakaoOAuthTokenResponse.refreshToken
            )
        }
    }
}
