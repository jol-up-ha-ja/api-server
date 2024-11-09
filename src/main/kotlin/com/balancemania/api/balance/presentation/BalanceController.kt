package com.balancemania.api.balance.presentation

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.balance.application.BalanceFacade
import com.balancemania.api.balance.model.request.CreateBalanceRequest
import com.balancemania.api.balance.model.request.PostWeightRequest
import com.balancemania.api.common.dto.ManiaPageRequest
import com.balancemania.api.extension.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Tag(name = "균형 정보 API", description = "균형 정보 API")
@RestController
@RequestMapping(value = ["/api/v1/balances"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BalanceController(
    private val balanceFacade: BalanceFacade,
) {
    @Operation(summary = "균형 정보 조회")
    @GetMapping
    fun getBalances(
        user: AuthUser,
        @ParameterObject sliceRequest: ManiaPageRequest,
    ) = executeWithCoroutine { balanceFacade.getBalances(user, sliceRequest) }.wrapSlice()

    @Operation(summary = "균형 정보 단일 조회")
    @GetMapping("/{balanceId}")
    fun getBalance(
        user: AuthUser,
        @PathVariable balanceId: Long,
    ) = executeWithCoroutine { balanceFacade.getBalance(user, balanceId) }.wrapOk()

    @Operation(summary = "균형 정보 등록")
    @PostMapping
    fun createBalance(
        user: AuthUser,
        @RequestBody request: CreateBalanceRequest,
    ) = executeWithCoroutine { balanceFacade.createBalance(user, request) }.wrapCreated()

    @Operation(summary = "무게 정보 임시 등록")
    @PostMapping("/weight")
    fun postWeight(
        user: AuthUser,
        @RequestBody request: PostWeightRequest,
    ) = executeWithCoroutine { balanceFacade.postWeight(user, request) }.wrapCreated()

    @Operation(summary = "균형 정보 삭제")
    @DeleteMapping("/{balanceId}")
    fun deleteBalance(
        user: AuthUser,
        @PathVariable balanceId: Long,
    ) = executeWithCoroutine { balanceFacade.deleteBalance(user, balanceId) }.wrapVoid()
}
