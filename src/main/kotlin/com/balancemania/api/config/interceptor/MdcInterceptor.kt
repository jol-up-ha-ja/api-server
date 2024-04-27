package com.balancemania.api.config.interceptor

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.UUID

@Component
class MdcInterceptor : HandlerInterceptor {
    val logger = KotlinLogging.logger {  }
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val eventId = UUID.randomUUID().toString()
        MDC.put("traceId", eventId)
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?,
    ) {
        logger.info { "[${MDC.get("traceId")}] ${response.status} ${request.requestURI} " }
        super.postHandle(request, response, handler, modelAndView)
    }
}
