package com.balancemania.api.auth.model.response

data class TokenRefreshRequest(
    /** mania access token */
    val accessToken: String,
    /** mania refresh token */
    val refreshToken: String,
)
