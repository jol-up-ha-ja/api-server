package com.balancemania.api.balance.application

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.balance.model.BalanceModel
import com.balancemania.api.balance.model.resposne.GetBalanceResponse
import com.balancemania.api.common.dto.ManiaPageRequest
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidRequestException
import com.balancemania.api.extension.coExecute
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class BalanceFacade(
    private val balanceService: BalanceService,
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

        txTemplates.writer.coExecute{
            balanceService.deleteByIdSync(balanceId)
        }
    }
}
