package com.balancemania.api.balance.application

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.balance.domain.Balance
import com.balancemania.api.balance.model.BalanceModel
import com.balancemania.api.balance.model.request.CreateBalanceRequest
import com.balancemania.api.balance.model.resposne.GetBalanceResponse
import com.balancemania.api.common.dto.ManiaPageRequest
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.extension.coExecute
import com.balancemania.api.extension.toInteger
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class BalanceFacade(
    private val balanceService: BalanceService,
    private val mediaPipeService: MediaPipeService,
    private val txTemplates: TransactionTemplates,
) {
    suspend fun getBalances(user: AuthUser, sliceRequest: ManiaPageRequest): Slice<BalanceModel> {
        return balanceService.getBalancesByUid(user.uid, sliceRequest.toDefault())
            .map { balance -> BalanceModel.from(balance) }
    }

    suspend fun getBalance(user: AuthUser, balanceId: Long): GetBalanceResponse {
        return balanceService.findByIdAndUid(user.uid, balanceId)
            .let { balance -> GetBalanceResponse(BalanceModel.from(balance)) }
    }

    suspend fun deleteBalance(user: AuthUser, balanceId: Long) {
        balanceService.validateExistByIdAndUid(user.uid, balanceId)

        txTemplates.writer.coExecute {
            balanceService.deleteByIdSync(balanceId)
        }
    }

    suspend fun createBalance(user: AuthUser, request: CreateBalanceRequest): GetBalanceResponse {
        val analyzedData = mediaPipeService.requestImgAnalysis(
            frontImgUrl = request.frontImgUrl,
            sideImgUrl = request.sideImgUrl
        )

        return txTemplates.writer.coExecute {
            Balance(
                uid = user.uid,
                frontShoulderAngle = analyzedData.frontShoulderAngle,
                frontPelvisAngle = analyzedData.frontPelvisAngle,
                frontKneeAngle = analyzedData.frontKneeAngle,
                frontAnkleAngle = analyzedData.frontAnkleAngle,
                sideNeckAngle = analyzedData.sideNeckAngle,
                sideBodyAngle = analyzedData.sideBodyAngle,
                leftWeight = request.leftWeight.toInteger(),
                rightWeight = request.rightWeight.toInteger()
            ).run { balanceService.saveSync(this) }
        }.let { balance -> GetBalanceResponse(BalanceModel.from(balance)) }
    }
}
