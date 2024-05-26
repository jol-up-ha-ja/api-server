package com.balancemania.api.cache.key

import com.balancemania.api.common.util.toTypeReference
import com.fasterxml.jackson.core.type.TypeReference
import java.time.Duration

data class Cache<VALUE_TYPE>(
    val key: String,
    val type: TypeReference<VALUE_TYPE>,
    val duration: Duration,
) {
    companion object Factory {
        fun getRefreshTokenCache(key: String, ttl: Long): Cache<String> {
            return Cache(
                key = key,
                type = toTypeReference(),
                duration = Duration.ofSeconds(ttl)
            )
        }
    }
}
