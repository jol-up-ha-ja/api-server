package com.balancemania.api.auth.presentation

import com.balancemania.api.auth.application.OAuthFacade
import com.balancemania.api.auth.model.request.OAuthLoginRequest
import com.balancemania.api.auth.model.request.OAuthRegisterRequest
import com.balancemania.api.extension.wrapCreated
import com.balancemania.api.extension.wrapOk
import com.balancemania.api.user.domain.vo.OAuthProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "OAuth API", description = "OAuth API")
@RestController
@RequestMapping(value = ["/api/v1/oauth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class OAuthController(
    private val oAuthFacade: OAuthFacade,
) {

    /** 가입된 유저인지 체크합니다. */
    @Operation(summary = "register valid check")
    @GetMapping("/{provider}/sign-up/valid")
    fun checkRegisterValid(
        @PathVariable provider: OAuthProvider,
        @RequestParam accessToken: String,
    ) = oAuthFacade.checkRegisterValid(provider, accessToken).wrapOk()

    /** 회원가입을 합니다. */
    @Operation(summary = "register")
    @PostMapping("/{provider}/sign-up")
    fun register(
        @PathVariable provider: OAuthProvider,
        @RequestBody request: OAuthRegisterRequest,
        @RequestParam accessToken: String,
    ) = oAuthFacade.register(provider, accessToken, request).wrapCreated()

    /** 로그인을 합니다. */
    @Operation(summary = "login")
    @PostMapping("/{provider}/login")
    fun login(
        @PathVariable provider: OAuthProvider,
        @RequestBody request: OAuthLoginRequest,
    ) = oAuthFacade.login(provider, request).wrapOk()
}
