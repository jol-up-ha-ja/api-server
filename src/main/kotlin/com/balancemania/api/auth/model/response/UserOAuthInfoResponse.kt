package com.balancemania.api.auth.model.response

import com.balancemania.api.user.domain.User
import com.balancemania.api.user.domain.vo.OAuthProvider

data class UserOAuthInfoResponse(
    /** 유저 id */
    val id: Long,
    /** 유저 oauth provider */
    val oAuthProvider: OAuthProvider,
) {
    companion object {
        fun from(user: User): UserOAuthInfoResponse {
            return UserOAuthInfoResponse(
                id = user.id,
                oAuthProvider = user.oauthInfo.oAuthProvider
            )
        }
    }
}
