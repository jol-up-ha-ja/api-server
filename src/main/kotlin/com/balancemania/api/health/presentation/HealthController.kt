package com.balancemania.api.health.presentation

import com.balancemania.api.extension.wrapOk
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health Check")
@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class HealthController {
    val logger = KotlinLogging.logger { }

    @GetMapping("/health")
    fun health() = "health".wrapOk()
}
