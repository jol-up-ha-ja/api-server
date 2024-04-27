package com.balancemania.api.example

import com.balancemania.api.extension.withMDCContext
import com.balancemania.api.extension.wrapVoid
import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health Check")
@RestController
@RequestMapping(value = ["/"], produces = [MediaType.APPLICATION_JSON_VALUE])
class HelloController {
    val logger = KotlinLogging.logger {  }

    @GetMapping("/health")
    fun health(): ResponseEntity<Unit> {
        runBlocking {
            withMDCContext(Dispatchers.IO) {
                logger.info { "coroutine" }
            }
        }
        logger.info { "req thread" }

        return Unit.wrapVoid()
    }
}
