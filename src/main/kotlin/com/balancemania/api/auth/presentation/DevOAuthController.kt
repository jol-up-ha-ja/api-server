package com.balancemania.api.auth.presentation

import com.balancemania.api.auth.application.DevOAuthService
import com.balancemania.api.extension.executeWithCoroutine
import com.balancemania.api.extension.wrapOk
import com.balancemania.api.user.domain.vo.OAuthProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "개발용 OAuth API", description = "개발용 OAuth API")
@RestController
@RequestMapping(value = ["/api/v1/dev/oauth"], produces = [MediaType.APPLICATION_JSON_VALUE])
class DevOAuthController(
    private val devOAuthService: DevOAuthService,
) {
    /** oauth login link를 반환해줍니다. 개발용 */
    @Operation(tags = ["개발용 OAuth API"], summary = "dev oauth link")
    @GetMapping("/{provider}/link")
    fun getDevOAuthLoginLink(
        @PathVariable provider: OAuthProvider,
    ) = executeWithCoroutine { devOAuthService.getOAuthLoginLinkDev(provider).wrapOk() }

    /** oauth 토큰 받아옵니다. 개발용 */
    @Operation(tags = ["개발용 OAuth API"], summary = "dev oauth link")
    @GetMapping("/{provider}/token")
    fun getDevOAuthLogin(
        @PathVariable provider: OAuthProvider,
        @RequestParam code: String,
    ) = executeWithCoroutine { devOAuthService.getOAuthTokenDev(provider, code).wrapOk() }
}
