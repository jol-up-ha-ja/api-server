package com.balancemania.api.user.presentation

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.extension.wrapCreated
import com.balancemania.api.extension.wrapOk
import com.balancemania.api.user.application.UserFacade
import com.balancemania.api.user.model.request.UpdateUserInfoRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "유저 API", description = "유저 API")
@RestController
@RequestMapping(value = ["/api/v1/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userFacade: UserFacade,
) {
    /** token 기반으로 유저 정보를 조회 */
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/my-info")
    fun getUserInfo(
        user: AuthUser,
    ) = userFacade.getUserInfo(user).wrapOk()

    @Operation(summary = "유저 정보 수정")
    @PatchMapping("/{uid}")
    fun updateUserInfo(
        user: AuthUser,
        @PathVariable uid: Long,
        @RequestBody request: UpdateUserInfoRequest,
    ) = userFacade.updateUserInfo(user, uid, request).wrapCreated()
}
