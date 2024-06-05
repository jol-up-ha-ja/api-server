package com.balancemania.api.config.filter

import com.balancemania.api.common.MDC_KEY_TRACE_ID
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcFilter : Filter {
    val logger = KotlinLogging.logger { }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpReq = request as HttpServletRequest
        val httpRes = response as HttpServletResponse

        val traceId = UUID.randomUUID().toString()

        try {
            MDC.put(MDC_KEY_TRACE_ID, traceId)
            logger.info { "[${MDC.get(MDC_KEY_TRACE_ID)}] ${httpReq.method} ${httpReq.requestURI} " }

            chain.doFilter(request, response)
        } finally {
            logger.info { "[${MDC.get(MDC_KEY_TRACE_ID)}] ${httpRes.status} ${httpReq.requestURI} " }
            MDC.clear()
        }
    }
}
