package com.balancemania.api.user.application

import arrow.core.nel
import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.extension.coExecute
import com.balancemania.api.user.model.UserModel
import com.balancemania.api.user.model.request.UpdateUserInfoRequest
import com.balancemania.api.user.model.response.GetUserInfoResponse
import org.springframework.stereotype.Service

@Service
class UserFacade(
    private val userService: UserService,
    private val txTemplates: TransactionTemplates,
) {
    suspend fun getUserInfo(user: AuthUser) {
        return userService.findByIdOrThrow(user.uid)
            .let { user -> GetUserInfoResponse(UserModel.from(user)) }
    }

    suspend fun updateUserInfo(user: AuthUser, uid: Long, request: UpdateUserInfoRequest) {
        val user = userService.findByIdOrThrow(uid)

        txTemplates.writer.coExecute{
            user.apply {
                name = request.name
                gender = request.gender
                birth = request.birth
            }.run { userService.saveSync(user) }
        }
    }
}
