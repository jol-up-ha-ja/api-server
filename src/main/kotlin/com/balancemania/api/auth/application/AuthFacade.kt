package com.balancemania.api.auth.application

import arrow.fx.coroutines.parZip
import com.balancemania.api.auth.application.domain.RefreshToken
import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.auth.model.AuthUserImpl
import com.balancemania.api.auth.model.AuthUserToken
import com.balancemania.api.auth.model.TokenDto
import com.balancemania.api.auth.model.response.TokenRefreshRequest
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidTokenException
import com.balancemania.api.exception.NoAuthorityException
import com.balancemania.api.extension.coExecuteOrNull
import com.balancemania.api.user.application.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthFacade(
    private val userService: UserService,
    private val jwtTokenService: JwtTokenService,
    private val refreshTokenService: RefreshTokenService,
    private val oAuthService: OAuthService,
    private val eventPublisher: ApplicationEventPublisher,
    private val txTemplates: TransactionTemplates,
) {
    fun resolveAuthUser(token: AuthUserToken): Any {
        return jwtTokenService.verifyToken(token)
            .let { payload ->
                if (payload.type != "accessToken") {
                    throw InvalidTokenException(ErrorCode.INVALID_ACCESS_TOKEN)
                }

                val user = userService.findByIdOrThrowSync(payload.id)

                AuthUserImpl(uid = user.id)
            }
    }

    @Transactional
    suspend fun logout(user: AuthUser) {
        refreshTokenService.deleteByKey(user.uid.toString())
    }

    @Transactional
    suspend fun refreshToken(request: TokenRefreshRequest): TokenDto {
        val accessPayload = jwtTokenService.verifyTokenWithExtendedExpiredAt(request.accessToken)
        val refreshPayload = jwtTokenService.verifyRefreshToken(request.refreshToken)

        if (accessPayload.id != refreshPayload.id) {
            throw NoAuthorityException(ErrorCode.INVALID_TOKEN)
        }

        return parZip(
            { refreshTokenService.deleteByKey(refreshPayload.id.toString()) },
            { jwtTokenService.generateAccessAndRefreshToken(refreshPayload.id) }
        ) { _, tokenDto ->
            RefreshToken(
                uid = refreshPayload.id,
                refreshToken = tokenDto.refreshToken
            ).run { refreshTokenService.save(this) }

            tokenDto
        }
    }

    @Transactional
    suspend fun withdraw(authUser: AuthUser) {
        val user = userService.findByIdOrThrow(authUser.uid)

        coroutineScope {
            val txDeferred = async {
                txTemplates.writer.coExecuteOrNull {
                    user.apply {
                        this.oauthInfo = oauthInfo.withdrawOAuthInfo()
                    }.run { userService.saveSync(this) }
                }
            }

            val oAuthDeferred = async {
                oAuthService.withdraw(user.oauthInfo)
            }

            awaitAll(txDeferred, oAuthDeferred)
        }
    }
}
