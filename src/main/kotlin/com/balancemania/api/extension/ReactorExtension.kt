package com.balancemania.api.extension

import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.FailToExecuteException
import kotlinx.coroutines.reactive.awaitSingle
import reactor.core.publisher.Mono

suspend fun <T : Any> Mono<T>.awaitSingleWithMdc(): T {
    return this.contextWrite { it.insertMDC() }
        .awaitSingle()
}

fun <T : Any> Mono<T>.awaitWithMdc(): T {
    return this.contextWrite { it.insertMDC() }
        .block() ?: throw FailToExecuteException(ErrorCode.EXTERNAL_SERVER_ERROR)
}
