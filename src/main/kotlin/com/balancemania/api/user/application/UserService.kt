package com.balancemania.api.user.application

import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.NotFoundException
import com.balancemania.api.extension.withMDCContext
import com.balancemania.api.user.domain.User
import com.balancemania.api.user.domain.vo.OauthInfo
import com.balancemania.api.user.infrastructure.UserRepository
import kotlinx.coroutines.Dispatchers
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun saveSync(user: User): User {
        return userRepository.save(user)
    }

    fun findByIdOrThrowSync(uid: Long): User {
        return findByIdOrNullSync(uid) ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER_ERROR)
    }

    fun findByIdOrNullSync(uid: Long): User? {
        return userRepository.findByIdOrNull(uid)
    }

    suspend fun findByIdOrThrow(uid: Long): User {
        return findByIdOrNull(uid) ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER_ERROR)
    }

    suspend fun findByIdOrNull(uid: Long): User? {
        return withMDCContext(Dispatchers.IO) { userRepository.findByIdOrNull(uid) }
    }

    suspend fun validateNotRegistered(oauthInfo: OauthInfo) {
        existsByOAuthInfo(oauthInfo).takeUnless { isExists -> isExists }
            ?: throw NotFoundException(ErrorCode.ALREADY_REGISTERED_USER)
    }

    suspend fun existsByOAuthInfo(oauthInfo: OauthInfo): Boolean {
        return withMDCContext(Dispatchers.IO) { userRepository.existsByOauthInfo(oauthInfo) }
    }

    suspend fun findByOAuthInfoOrThrow(oauthInfo: OauthInfo): User {
        return findByOAuthInfoOrNull(oauthInfo) ?: throw NotFoundException(ErrorCode.NOT_FOUND_USER_ERROR)
    }

    suspend fun findByOAuthInfoOrNull(oauthInfo: OauthInfo): User? {
        return withMDCContext(Dispatchers.IO) { userRepository.findByOauthInfo(oauthInfo) }
    }
}
