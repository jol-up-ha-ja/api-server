package com.balancemania.api.auth.model.request

data class OAuthLoginRequest(
    /** oauth access token */
    val accessToken: String,
)
