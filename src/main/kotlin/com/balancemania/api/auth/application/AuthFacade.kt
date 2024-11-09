package com.balancemania.api.auth.application

import com.balancemania.api.auth.application.domain.RefreshToken
import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.auth.model.AuthUserImpl
import com.balancemania.api.auth.model.AuthUserToken
import com.balancemania.api.auth.model.TokenDto
import com.balancemania.api.auth.model.response.TokenRefreshRequest
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.NoAuthorityException
import com.balancemania.api.extension.executeNotNull
import com.balancemania.api.user.application.UserService
import com.balancemania.api.user.domain.vo.UserStatusType
import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.MDCContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthFacade(
    private val userService: UserService,
    private val jwtTokenService: JwtTokenService,
    private val refreshTokenService: RefreshTokenService,
    private val oAuthService: OAuthService,
    private val txTemplates: TransactionTemplates,
) {
    fun resolveAuthUser(token: AuthUserToken): Any {
        return AuthUserImpl(uid = 200000L)
//        return jwtTokenService.verifyToken(token)
//            .let { payload ->
//                if (payload.type != "accessToken") {
//                    throw InvalidTokenException(ErrorCode.INVALID_ACCESS_TOKEN)
//                }
//
//                val user = userService.findByIdOrThrowSync(payload.id)
//
//                when (user.statusType) {
//                    UserStatusType.ACTIVE -> {
//                        /** 별도 처리 없음 */
//                    }
//
//                    UserStatusType.DELETED -> throw InvalidRequestException(ErrorCode.WITHDRAW_USER_ERROR)
//                    UserStatusType.BANISHED -> throw InvalidRequestException(ErrorCode.BANISHED_USER_ERROR)
//                    UserStatusType.RESTRICTED_7_DAYS -> throw InvalidRequestException(ErrorCode.RESTRICTED_7_DAYS_USER_ERROR)
//                }
//
//                AuthUserImpl(uid = user.id)
//            }
    }

    @Transactional
    fun logout(user: AuthUser) {
        refreshTokenService.deleteByKey(user.uid.toString())
    }

    @Transactional
    fun refreshToken(request: TokenRefreshRequest): TokenDto {
        val accessPayload = jwtTokenService.verifyTokenWithExtendedExpiredAt(request.accessToken)
        val refreshPayload = jwtTokenService.verifyRefreshToken(request.refreshToken)

        if (accessPayload.id != refreshPayload.id) {
            throw NoAuthorityException(ErrorCode.INVALID_TOKEN)
        }

        CoroutineScope(Dispatchers.IO + MDCContext()).launch {
            refreshTokenService.deleteByKey(refreshPayload.id.toString())
        }

        return jwtTokenService.generateAccessAndRefreshToken(refreshPayload.id).also {
            RefreshToken(
                uid = refreshPayload.id,
                refreshToken = it.refreshToken
            ).run { refreshTokenService.save(this) }
        }
    }

    @Transactional
    fun withdraw(authUser: AuthUser) {
        val user = userService.findByIdOrThrow(authUser.uid)

        oAuthService.withdraw(user.oauthInfo)

        txTemplates.writer.executeNotNull() {
            user.apply {
                this.oauthInfo = oauthInfo.withdrawOAuthInfo()
                this.statusType = UserStatusType.DELETED
            }.run { userService.saveSync(this) }
        }
    }
}
