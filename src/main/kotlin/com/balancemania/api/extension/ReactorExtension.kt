package com.balancemania.api.extension

import kotlinx.coroutines.reactive.awaitSingle
import reactor.core.publisher.Mono

suspend fun <T : Any> Mono<T>.awaitSingleWithMdc(): T {
    return this.contextWrite { it.insertMDC() }
        .awaitSingle()
}
