package com.balancemania.api.auth.application.infrastructure

import com.balancemania.api.auth.application.domain.RefreshToken
import com.balancemania.api.cache.key.Cache
import com.balancemania.api.cache.service.CacheService
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository(
    private val cacheService: CacheService,
) {
    suspend fun save(value: RefreshToken, ttl: Long) {
        cacheService.set(
            cache = getCache(value.uid.toString(), ttl),
            value = value.refreshToken
        )
    }

    suspend fun deleteByKey(key: String) {
        cacheService.delete(cache = getCache(key, 0))
    }

    private fun getCache(key: String, ttl: Long): Cache<String> {
        return Cache.getRefreshTokenCache(key = key, ttl = ttl)
    }
}
