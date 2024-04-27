package com.balancemania.api.exception.advice

import com.balancemania.api.common.dto.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.hibernate.TypeMismatchException
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException
import kotlin.coroutines.cancellation.CancellationException

@RestControllerAdvice
class ExceptionHandler {
    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(WebExchangeBindException::class)
    protected fun handleWebExchangeBindException(
        e: WebExchangeBindException,
        request:  HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.warn { "WebExchangeBindException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }

    @ExceptionHandler(DecodingException::class)
    protected fun handleDecodingException(
        e: DecodingException,
        request:  HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.warn { "DecodingException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(
        e: ConstraintViolationException,
        request:  HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.warn { "ConstraintViolationException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }

    @ExceptionHandler(ServerWebInputException::class)
    protected fun handleServerWebInputException(
        e: ServerWebInputException,
        request:  HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.warn { "ServerWebInputException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }

    @ExceptionHandler(TypeMismatchException::class)
    protected fun handleTypeMismatchException(
        e: TypeMismatchException,
        request:  HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.warn { "TypeMismatchException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }

    /**
     * Coroutines Cancellation
     * - [Kotlin Docs](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines.cancellation/)
     */
    @ExceptionHandler(CancellationException::class)
    protected fun handleCancellationException(
        e: CancellationException,
        request:  HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.warn { "CancellationException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(
        e: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        logger.error { "CancellationException ${e.message}, requestUri=${request.requestURI}" }
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse.of(e))
    }
}