package com.balancemania.api.extension

import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.FailToExecuteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate
import kotlin.coroutines.CoroutineContext

suspend fun <RETURN> TransactionTemplate.coExecute(
    coroutineContext: CoroutineContext = Dispatchers.IO,
    actions: TransactionCallback<RETURN>,
): RETURN {
    val transactionTemplate: TransactionTemplate = this

    return withContext(coroutineContext) {
        transactionTemplate.execute(actions)
    } ?: throw FailToExecuteException(ErrorCode.FAIL_TO_TRANSACTION_TEMPLATE_EXECUTE_ERROR)
}

suspend fun <RETURN> TransactionTemplate.coExecuteOrNull(
    coroutineContext: CoroutineContext = Dispatchers.IO,
    actions: TransactionCallback<RETURN>,
): RETURN? {
    val transactionTemplate: TransactionTemplate = this

    return withContext(coroutineContext) {
        transactionTemplate.execute(actions)
    }
}