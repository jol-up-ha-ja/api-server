package com.balancemania.api.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.balancemania.api.auth.model.AuthUserToken
import com.balancemania.api.auth.model.AuthUserTokenPayload
import com.balancemania.api.auth.model.TokenDto
import com.balancemania.api.config.auth.JwtConfig
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidTokenException
import com.balancemania.api.extension.decodeBase64
import com.balancemania.api.extension.mapper
import com.balancemania.api.extension.toInstant
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Date

private const val ACCESS_TOKEN = "accessToken"
private const val REFRESH_TOKEN = "refreshToken"

@Service
class JwtTokenService(
    private val jwtConfig: JwtConfig,
) {
    private val logger = KotlinLogging.logger {}

    private val accessJwtVerifier = JWT
        .require(Algorithm.HMAC256(jwtConfig.secretKey))
        .withIssuer(jwtConfig.issuer)
        .withAudience(jwtConfig.audience)
        .withClaim("type", ACCESS_TOKEN)
        .build()

    private val accessJwtVerifierWithExtendedExpiredAt = JWT
        .require(Algorithm.HMAC256(jwtConfig.secretKey))
        .withIssuer(jwtConfig.issuer)
        .withAudience(jwtConfig.audience)
        .withClaim("type", ACCESS_TOKEN)
        .acceptExpiresAt(jwtConfig.refreshExp.toLong())
        .build()

    private val refreshJwtVerifier = JWT
        .require(Algorithm.HMAC256(jwtConfig.secretKey))
        .withIssuer(jwtConfig.issuer)
        .withAudience(jwtConfig.audience)
        .withClaim("type", REFRESH_TOKEN)
        .build()

    fun createToken(id: Long, tokenExpiredAt: LocalDateTime): String {
        return JWT.create().apply {
            this.withIssuer(jwtConfig.issuer)
            this.withAudience(jwtConfig.audience)
            this.withClaim("id", id)
            this.withClaim("type", ACCESS_TOKEN)
            this.withExpiresAt(Date.from(tokenExpiredAt.toInstant()))
        }.sign(Algorithm.HMAC256(jwtConfig.secretKey))
    }

    fun verifyToken(token: AuthUserToken): AuthUserTokenPayload {
        val payload = accessJwtVerifier.verify(token.value)
            .payload
            .decodeBase64()

        return mapper.readValue(payload)
    }

    fun verifyTokenWithExtendedExpiredAt(token: String): AuthUserTokenPayload {
        val payload = runCatching { accessJwtVerifierWithExtendedExpiredAt.verify(token).payload.decodeBase64() }
            .getOrNull() ?: throw InvalidTokenException(ErrorCode.INVALID_TOKEN)

        return mapper.readValue(payload)
    }

    fun createRefreshToken(id: Long, refreshTokenExpiresAt: LocalDateTime): String {
        return JWT.create().apply {
            this.withIssuer(jwtConfig.issuer)
            this.withAudience(jwtConfig.audience)
            this.withClaim("id", id)
            this.withClaim("type", REFRESH_TOKEN)
            this.withExpiresAt(Date.from(refreshTokenExpiresAt.toInstant()))
        }.sign(Algorithm.HMAC256(jwtConfig.secretKey))
    }

    fun verifyRefreshToken(refreshToken: String): AuthUserTokenPayload {
        val payload = runCatching { refreshJwtVerifier.verify(refreshToken).payload.decodeBase64() }
            .getOrNull() ?: throw InvalidTokenException(ErrorCode.INVALID_REFRESH_TOKEN)

        return mapper.readValue(payload)
    }

    fun generateAccessAndRefreshToken(uid: Long): TokenDto {
        val issuedAt = LocalDateTime.now()
        val accessTokenExpiresIn = issuedAt.plusSeconds(jwtConfig.accessExp.toLong())
        val accessToken = createToken(uid, accessTokenExpiresIn)

        val refreshTokenExpiresIn = issuedAt.plusSeconds(jwtConfig.refreshExp.toLong())
        val refreshToken = createRefreshToken(uid, refreshTokenExpiresIn)

        return TokenDto(
            accessToken = accessToken,
            accessTokenExp = accessTokenExpiresIn,
            refreshToken = refreshToken,
            refreshTokenExp = refreshTokenExpiresIn
        )
    }
}
