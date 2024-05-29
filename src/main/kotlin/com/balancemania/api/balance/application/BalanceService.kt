package com.balancemania.api.balance.application

import com.balancemania.api.balance.domain.Balance
import com.balancemania.api.balance.infrastructure.BalanceRepository
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidRequestException
import com.balancemania.api.extension.withMDCContext
import kotlinx.coroutines.Dispatchers
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val balanceRepository: BalanceRepository,
) {
    fun deleteByIdSync(id: Long) {
        balanceRepository.deleteById(id)
    }

    suspend fun getBalancesByUid(uid: Long, pageable: Pageable): Slice<Balance> {
        return withMDCContext(Dispatchers.IO) {
            balanceRepository.getBalancesByUid(uid, pageable)
        }
    }

    suspend fun findByIdAndUid(uid: Long, id: Long): Balance {
        return findByIdOrThrow(id).takeIf { balance -> balance.uid == uid }
            ?: throw InvalidRequestException(ErrorCode.NO_AUTHORITY_ERROR)
    }

    suspend fun findByIdOrThrow(id: Long): Balance {
        return findByIdOrNull(id) ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_BALANCE_ERROR)
    }

    suspend fun findByIdOrNull(id: Long): Balance? {
        return withMDCContext(Dispatchers.IO) {
            balanceRepository.findByIdOrNull(id)
        }
    }

    suspend fun validateExistByIdAndUid(uid: Long, id: Long) {
        existByIdAndUid(uid, id).takeIf { it } ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_BALANCE_ERROR)
    }

    suspend fun existByIdAndUid(uid: Long, id: Long): Boolean {
        return withMDCContext(Dispatchers.IO) { balanceRepository.existsByIdAndUid(uid, id) }
    }
}
