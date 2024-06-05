package com.balancemania.api.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> withMDCContext(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend () -> T,
): T {
    return withContext(context + MDCContext()) { block() }
}

fun <T> executeWithCoroutine(block: suspend () -> T): T {
    return runBlocking(Dispatchers.Unconfined + MDCContext()) { block() }
}
