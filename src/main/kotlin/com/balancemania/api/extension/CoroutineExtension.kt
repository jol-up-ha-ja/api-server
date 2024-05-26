package com.balancemania.api.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.MDC
import kotlin.coroutines.CoroutineContext

suspend fun <T> withMDCContext(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend () -> T,
): T {
    val contextMap = MDC.getCopyOfContextMap() ?: emptyMap()
    return withContext(context + MDCContext(contextMap)) { block() }
}

fun <T> executeWithCoroutine(block: suspend () -> T): T {
    val contextMap = MDC.getCopyOfContextMap() ?: emptyMap()
    return runBlocking(Dispatchers.Default + MDCContext(contextMap)) { block() }
}
