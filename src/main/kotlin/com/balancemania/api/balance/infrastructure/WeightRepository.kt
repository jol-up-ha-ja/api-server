package com.balancemania.api.balance.infrastructure

import com.balancemania.api.balance.domain.Weight
import com.balancemania.api.cache.key.Cache
import com.balancemania.api.cache.service.CacheService
import org.springframework.stereotype.Component

@Component
class WeightRepository(
    private val cacheService: CacheService,
) {
    private fun getCache(key: String): Cache<Weight> {
        return Cache.getWeightCache(key = key)
    }

    fun save(value: Weight) {
        cacheService.set(
            cache = getCache("CACHE_WEIGHT_${value.uid}"),
            value = value
        )
    }

    fun getOrNull(uid: Long): Weight? {
        return cacheService.getOrNull(getCache("CACHE_WEIGHT_$uid"))
    }

    fun delete(uid: Long) {
        cacheService.delete(cache = getCache("CACHE_WEIGHT_$uid"))
    }
}
