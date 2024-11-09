package com.balancemania.api.balance.application

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.balance.domain.Balance
import com.balancemania.api.balance.domain.Weight
import com.balancemania.api.balance.model.BalanceModel
import com.balancemania.api.balance.model.request.CreateBalanceRequest
import com.balancemania.api.balance.model.request.PostWeightRequest
import com.balancemania.api.balance.model.resposne.GetBalanceResponse
import com.balancemania.api.common.dto.ManiaPageRequest
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidRequestException
import com.balancemania.api.extension.executeNotNull
import com.balancemania.api.extension.toInteger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.slf4j.MDCContext
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class BalanceFacade(
    private val balanceService: BalanceService,
    private val mediaPipeService: MediaPipeService,
    private val weightService: WeightService,
    private val txTemplates: TransactionTemplates,
) {
    fun getBalances(user: AuthUser, sliceRequest: ManiaPageRequest): Slice<BalanceModel> {
        return balanceService.getBalancesByUid(user.uid, sliceRequest.toDefault())
            .map { balance -> BalanceModel.from(balance) }
    }

    fun getBalance(user: AuthUser, balanceId: Long): GetBalanceResponse {
        return GetBalanceResponse(BalanceModel.from(balanceService.findByIdAndUid(user.uid, balanceId)))
    }

    fun deleteBalance(user: AuthUser, balanceId: Long) {
        balanceService.validateExistByIdAndUid(user.uid, balanceId)

        txTemplates.writer.executeNotNull() {
            balanceService.deleteByIdSync(balanceId)
        }
    }

    fun createBalance(user: AuthUser, request: CreateBalanceRequest): GetBalanceResponse {
        val weight = weightService.getWeightOrNull(user)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_WEIGHT_ERROR)

        val analyzedData = mediaPipeService.requestImgAnalysis(
            frontImgKey = request.frontImgKey,
            sideImgKey = request.sideImgKey
        )

        val balance = txTemplates.writer.executeNotNull {
            Balance(
                uid = user.uid,
                frontShoulderAngle = analyzedData.frontShoulderAngle.toInteger(),
                frontPelvisAngle = analyzedData.frontPelvisAngle.toInteger(),
                frontKneeAngle = analyzedData.frontKneeAngle.toInteger(),
                frontAnkleAngle = analyzedData.frontAnkleAngle.toInteger(),
                sideNeckAngle = analyzedData.sideNeckAngle.toInteger(),
                sideBodyAngle = analyzedData.sideBodyAngle.toInteger(),
                leftWeight = weight.leftWeight,
                rightWeight = weight.rightWeight
            ).run { balanceService.saveSync(this) }
        }

        CoroutineScope(Dispatchers.IO + MDCContext()).launch {
            weightService.delete(user)
        }

        return GetBalanceResponse(BalanceModel.from(balance))
    }

    fun postWeight(user: AuthUser, request: PostWeightRequest) {
        txTemplates.writer.executeNotNull {
            Weight(
                uid = user.uid,
                rightWeight = request.rightWeight.toInteger(),
                leftWeight = request.leftWeight.toInteger()
            ).also { weightService.save(it) }
        }
    }
}
