package com.balancemania.api.s3.presentation

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.extension.executeWithCoroutine
import com.balancemania.api.extension.wrapOk
import com.balancemania.api.s3.application.S3Facade
import com.balancemania.api.s3.model.ImageType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "AWS S3 API", description = "AWS S3 API")
@RestController
@RequestMapping(value = ["/api/v1/s3"], produces = [MediaType.APPLICATION_JSON_VALUE])
class S3Controller(
    private val s3Facade: S3Facade,
) {
    @Operation(summary = "presigned url 발급")
    @GetMapping("/presigned-url")
    fun getPresignedUrl(
        user: AuthUser,
        @RequestParam(defaultValue = "jpg") imgType: ImageType,
    ) = executeWithCoroutine { s3Facade.getPresignedUrl(user, imgType) }.wrapOk()
}
