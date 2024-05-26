package com.balancemania.api.auth.model

import com.balancemania.api.client.oauth.kakao.model.KakaoOAuthUserInfoResponse
import com.balancemania.api.user.domain.vo.OAuthProvider
import com.balancemania.api.user.domain.vo.OauthInfo

/** oauth 정보 dto */
data class OAuthUserInfoDto(
    /** oauth 정보 */
    val oauthInfo: OauthInfo,
) {
    companion object {
        fun fromKakao(kakaoOAuthUserInfoResponse: KakaoOAuthUserInfoResponse): OAuthUserInfoDto {
            return OAuthUserInfoDto(
                OauthInfo(
                    oAuthId = kakaoOAuthUserInfoResponse.id,
                    oAuthProvider = OAuthProvider.KAKAO
                )
            )
        }
    }
}
