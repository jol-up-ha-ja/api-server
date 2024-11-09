package com.balancemania.api.auth.application

import com.balancemania.api.auth.application.domain.RefreshToken
import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.auth.model.TokenDto
import com.balancemania.api.auth.model.request.OAuthLoginRequest
import com.balancemania.api.auth.model.request.OAuthRegisterRequest
import com.balancemania.api.auth.model.response.AbleRegisterResponse
import com.balancemania.api.auth.model.response.UserOAuthInfoResponse
import com.balancemania.api.config.database.TransactionTemplates
import com.balancemania.api.extension.executeNotNull
import com.balancemania.api.user.application.UserService
import com.balancemania.api.user.domain.User
import com.balancemania.api.user.domain.vo.OAuthProvider
import com.balancemania.api.user.domain.vo.UserStatusType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

@Service
class OAuthFacade(
    private val userService: UserService,
    private val refreshTokenService: RefreshTokenService,
    private val oAuthService: OAuthService,
    private val txTemplates: TransactionTemplates,
    private val jwtTokenService: JwtTokenService,
) {
    val logger = KotlinLogging.logger {}

    /** 회원가입 가능 여부 체크. */
    fun checkRegisterValid(provider: OAuthProvider, accessToken: String): AbleRegisterResponse {
        val oauthInfo = oAuthService.getOAuthInfo(provider, accessToken)

        val isExistUser = userService.existsByOAuthInfo(oauthInfo)

        return AbleRegisterResponse(!isExistUser)
    }

    /** 회원가입 */
    fun register(
        provider: OAuthProvider,
        accessToken: String,
        request: OAuthRegisterRequest,
    ): TokenDto {
        val oauthInfo = oAuthService.getOAuthInfo(provider, accessToken)

        userService.validateNotRegistered(oauthInfo)

        val user = txTemplates.writer.executeNotNull {
            User(
                oauthInfo = oauthInfo,
                name = request.name,
                gender = request.gender,
                birth = request.getBirth(),
                statusType = UserStatusType.ACTIVE
            ).run { userService.saveSync(this) }
        }

        return generateTokenDto(user.id)
    }

    /** 로그인 */
    fun login(
        provider: OAuthProvider,
        request: OAuthLoginRequest,
    ): TokenDto {
        val oauthInfo = oAuthService.getOAuthInfo(provider, request.accessToken)
        val user = userService.findByOAuthInfoOrThrow(oauthInfo)

        return generateTokenDto(user.id)
    }

    private fun generateTokenDto(uid: Long): TokenDto {
        return jwtTokenService.generateAccessAndRefreshToken(uid).also {
            RefreshToken(uid = uid, refreshToken = it.refreshToken)
                .run { refreshTokenService.save(this) }
        }
    }

    fun getOAuthInfo(user: AuthUser): UserOAuthInfoResponse {
        return userService.findByIdOrThrow(user.uid).run { UserOAuthInfoResponse.from(this) }
    }
}
