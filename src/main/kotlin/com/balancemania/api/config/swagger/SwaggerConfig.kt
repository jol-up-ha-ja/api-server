package com.balancemania.api.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig (
    private val buildProperties: BuildProperties,
) {

    @Bean
    fun openApi(): OpenAPI {
//        val securityRequirement = SecurityRequirement().addList(AUTH_TOKEN_KEY)
        return OpenAPI()
//            .components(authSetting())
//            .security(listOf(securityRequirement))
            .addServersItem(Server().url("/"))
            .info(
                Info()
                    .title(buildProperties.name)
                    .version(buildProperties.version)
                    .description("Balance Mania Rest API Docs")
            )
    }

    private fun authSetting(): Components {
        return Components()
            .addSecuritySchemes(
                "X-MANIA-AUTH-TOKEN",
                SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .`in`(SecurityScheme.In.HEADER)
                    .name("X-MANIA-AUTH-TOKEN")
            )
    }
}
