package com.balancemania.api.balance.application

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.balance.domain.Weight
import com.balancemania.api.balance.infrastructure.WeightRepository
import org.springframework.stereotype.Service

@Service
class WeightService(
    private val weightRepository: WeightRepository,
) {
    suspend fun save(weight: Weight) {
        weightRepository.save(weight)
    }

    suspend fun getWeightOrNull(user: AuthUser): Weight? {
        return weightRepository.getOrNull(user.uid)
    }

    suspend fun delete(user: AuthUser) {
        weightRepository.delete(user.uid)
    }
}
