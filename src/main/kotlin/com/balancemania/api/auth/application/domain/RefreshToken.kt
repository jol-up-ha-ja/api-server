package com.balancemania.api.auth.application.domain

/** refresh token */
data class RefreshToken(
    /** 리프레시 토큰 해당 유저 */
    val uid: Long,
    /** 리프레시 토큰 */
    var refreshToken: String,
)
