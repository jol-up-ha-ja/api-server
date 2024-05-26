package com.balancemania.api.config.web

import com.balancemania.api.auth.application.AuthFacade
import com.balancemania.api.auth.resolver.UserResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val authFacade: AuthFacade,
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(UserResolver(authFacade))
    }
}
