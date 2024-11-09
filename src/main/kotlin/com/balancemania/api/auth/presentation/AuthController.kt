package com.balancemania.api.auth.presentation

import com.balancemania.api.auth.application.AuthFacade
import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.auth.model.response.TokenRefreshRequest
import com.balancemania.api.extension.wrapOk
import com.balancemania.api.extension.wrapVoid
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth API", description = "Auth API")
@RestController
@RequestMapping(value = ["/api/v1/auth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController(
    private val authFacade: AuthFacade,
) {
    /** 로그아웃을 합니다 */
    @Operation(summary = "logout")
    @PostMapping("/logout")
    fun logout(
        authUser: AuthUser,
    ) = authFacade.logout(authUser).wrapVoid()

    /** 토큰 재발급 */
    @Operation(summary = "token refresh")
    @PostMapping("/token/refresh")
    fun tokenRefresh(
        @RequestBody request: TokenRefreshRequest,
    ) = authFacade.refreshToken(request).wrapOk()

    /**
     *  회원 탈퇴
     */
    @Operation(summary = "withdraw")
    @PostMapping("/withdraw")
    fun tokenRefresh(
        authUser: AuthUser,
    ) = authFacade.withdraw(authUser).wrapVoid()
}
