package com.balancemania.api.auth.application

import com.balancemania.api.auth.application.domain.RefreshToken
import com.balancemania.api.auth.application.infrastructure.RefreshTokenRepository
import com.balancemania.api.config.auth.JwtConfig
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtConfig: JwtConfig,
) {
    fun deleteByKey(key: String) {
        refreshTokenRepository.deleteByKey(key)
    }

    fun save(token: RefreshToken) {
        refreshTokenRepository.save(token, jwtConfig.refreshExp.toLong())
    }
}
