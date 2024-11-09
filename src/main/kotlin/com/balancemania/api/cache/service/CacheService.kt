package com.balancemania.api.cache.service

import com.balancemania.api.cache.key.Cache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface CacheService {
    /** key-value */
    fun <VALUE_TYPE : Any> set(cache: Cache<VALUE_TYPE>, value: VALUE_TYPE)
    fun <VALUE_TYPE : Any> getOrNull(cache: Cache<VALUE_TYPE>): VALUE_TYPE?
    fun <VALUE_TYPE : Any> delete(cache: Cache<VALUE_TYPE>)

    companion object {
        /** key-value */
        fun <VALUE_TYPE : Any> CacheService.set(
            cache: Cache.Factory.() -> Cache<VALUE_TYPE>,
            value: VALUE_TYPE,
        ) = set(cache(Cache), value)

        fun <VALUE_TYPE : Any> CacheService.getOrNull(
            cache: Cache.Factory.() -> Cache<VALUE_TYPE>,
        ) = getOrNull(cache(Cache))

        fun <VALUE_TYPE : Any> CacheService.get(
            cache: Cache.Factory.() -> Cache<VALUE_TYPE>,
            onCacheMissResume: () -> VALUE_TYPE,
        ): VALUE_TYPE = cache(Cache).let { cacheInfo ->
            getOrNull(cacheInfo) ?: onCacheMissResume().also { value -> launchSet(coroutineContext, cacheInfo, value) }
        }

        fun <VALUE_TYPE : Any> CacheService.launchSet(
            context: CoroutineContext = EmptyCoroutineContext,
            cache: Cache<VALUE_TYPE>,
            value: VALUE_TYPE,
        ) = CoroutineScope(context + Job()).launch { set(cache, value) }
    }
}
