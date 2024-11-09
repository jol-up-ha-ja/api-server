package com.balancemania.api.balance.application

import com.balancemania.api.balance.domain.Balance
import com.balancemania.api.balance.infrastructure.BalanceRepository
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidRequestException
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val balanceRepository: BalanceRepository,
) {
    fun saveSync(balance: Balance): Balance {
        return balanceRepository.save(balance)
    }

    fun deleteByIdSync(id: Long) {
        balanceRepository.deleteById(id)
    }

    fun getBalancesByUid(uid: Long, pageable: Pageable): Slice<Balance> {
        return balanceRepository.getBalancesByUid(uid, pageable)
    }

    fun findByIdAndUid(uid: Long, id: Long): Balance {
        return findByIdOrThrow(id).takeIf { balance -> balance.uid == uid }
            ?: throw InvalidRequestException(ErrorCode.NO_AUTHORITY_ERROR)
    }

    fun findByIdOrThrow(id: Long): Balance {
        return findByIdOrNull(id) ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_BALANCE_ERROR)
    }

    fun findByIdOrNull(id: Long): Balance? {
        return balanceRepository.findByIdOrNull(id)
    }

    fun validateExistByIdAndUid(uid: Long, id: Long) {
        existByIdAndUid(uid, id).takeIf { it } ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_BALANCE_ERROR)
    }

    fun existByIdAndUid(uid: Long, id: Long): Boolean {
        return balanceRepository.existsByIdAndUid(uid, id)
    }
}
