package com.balancemania.api.auth.resolver

import com.balancemania.api.auth.application.AuthFacade
import com.balancemania.api.auth.model.AUTH_TOKEN_KEY
import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.auth.model.AuthUserToken
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.InvalidTokenException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class UserResolver(
    private val authFacade: AuthFacade,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == AuthUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        val httpRequest: HttpServletRequest = webRequest.nativeRequest as HttpServletRequest
        val token = resolveToken(httpRequest)

        return authFacade.resolveAuthUser(token)
    }

    private fun resolveToken(request: HttpServletRequest): AuthUserToken {
        val authUserToken = runCatching {
            request.getHeader(AUTH_TOKEN_KEY).let { token -> AuthUserToken.from(token) }
        }.onFailure { throw InvalidTokenException(ErrorCode.INVALID_TOKEN) }
            .getOrThrow()


        return authUserToken
    }
}
