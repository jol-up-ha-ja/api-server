package com.balancemania.api.cache.service

import com.balancemania.api.cache.key.Cache
import com.balancemania.api.extension.mapper
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class CacheServiceImpl(
    private val stringRedisTemplate: StringRedisTemplate,
) : CacheService {
    private val logger = KotlinLogging.logger { }
    private val keyValueOps = stringRedisTemplate.opsForValue()

    override fun <VALUE_TYPE : Any> set(cache: Cache<VALUE_TYPE>, value: VALUE_TYPE) {
        runCatching {
            keyValueOps.set(
                cache.key,
                mapper.writeValueAsString(value),
                cache.duration
            )
        }.onFailure { e ->
            when (e) {
                is CancellationException -> logger.debug { "Redis Set job cancelled." }
                else -> logger.error(e) { "fail to set data from redis. key : ${cache.key}" }
            }
        }.getOrNull()
    }

    override fun <VALUE_TYPE : Any> getOrNull(cache: Cache<VALUE_TYPE>): VALUE_TYPE? {
        return runCatching {
            val jsonValue = keyValueOps
                .get(cache.key)

            when (jsonValue.isNullOrBlank()) {
                true -> null
                false -> mapper.readValue(jsonValue, cache.type)
            }
        }.onFailure { e ->
            when (e) {
                is CancellationException -> logger.debug { "Redis Read job cancelled." }
                else -> logger.error(e) { "fail to read data from redis. key : ${cache.key}" }
            }
        }.getOrNull()
    }

    override fun <VALUE_TYPE : Any> delete(cache: Cache<VALUE_TYPE>) {
        runCatching {
            keyValueOps.getAndDelete(cache.key)
        }.onFailure { e ->
            when (e) {
                is CancellationException -> logger.debug { "Redis Delete job cancelled." }
                else -> logger.error(e) { "fail to delete data from redis. key : ${cache.key}" }
            }
        }.getOrNull()
    }
}
