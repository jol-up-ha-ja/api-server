package com.balancemania.api.exception.advice

import com.balancemania.api.common.dto.ErrorResponse
import com.balancemania.api.exception.ManiaException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ManiaExceptionHandler {
    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(ManiaException::class)
    protected fun handleBusinessException(e: ManiaException): ResponseEntity<ErrorResponse> {
        logger.warn { "BusinessException ${e.message}" }
        val response = ErrorResponse(
            errorCode = e.errorCode.name,
            reason = e.message ?: e.errorCode.description,
            extra = e.extra
        )
        return ResponseEntity(response, e.errorCode.status)
    }
}