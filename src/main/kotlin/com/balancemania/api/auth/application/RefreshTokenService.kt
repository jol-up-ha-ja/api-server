package com.balancemania.api.auth.application

import com.balancemania.api.auth.application.domain.RefreshToken
import com.balancemania.api.auth.application.infrastructure.RefreshTokenRepository
import com.balancemania.api.config.auth.JwtConfig
import com.balancemania.api.extension.withMDCContext
import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtConfig: JwtConfig,
) {
    suspend fun deleteByKey(key: String) {
        refreshTokenRepository.deleteByKey(key)
    }

    suspend fun save(token: RefreshToken) {
        withMDCContext(Dispatchers.IO) {
            refreshTokenRepository.save(token, jwtConfig.refreshExp.toLong())
        }
    }
}
