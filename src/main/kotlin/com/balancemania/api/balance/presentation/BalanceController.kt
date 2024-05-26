package com.balancemania.api.balance.presentation

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.common.dto.ManiaPageRequest
import com.balancemania.api.extension.executeWithCoroutine
import com.balancemania.api.extension.wrapOk
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "균형 정보 API", description = "균형 정보 API")
@RestController
@RequestMapping(value = ["/api/v1/balances"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BalanceController {
    @Operation(summary = "균형 정보 조회")
    @GetMapping
    fun getBalances(
        user: AuthUser,
        @ParameterObject sliceRequest: ManiaPageRequest,
    ) = executeWithCoroutine { Unit.wrapOk() }

    @Operation(summary = "균형 정보 단일 조회")
    @GetMapping("/{balanceId}")
    fun getBalance(
        user: AuthUser,
        @PathVariable balanceId: Long,
    ) = executeWithCoroutine { Unit.wrapOk() }

    @Operation(summary = "균형 정보 등록")
    @PostMapping
    fun updateUserInfo(
        user: AuthUser,
    ) = executeWithCoroutine { Unit.wrapOk() }

    @Operation(summary = "균형 정보 삭제")
    @DeleteMapping("/{balanceId}")
    fun deleteBalance(
        user: AuthUser,
        @PathVariable balanceId: Long,
    ) = executeWithCoroutine { Unit.wrapOk() }
}
