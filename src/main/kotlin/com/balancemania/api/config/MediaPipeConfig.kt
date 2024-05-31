package com.balancemania.api.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "media-pipe")
@ConfigurationPropertiesBinding
data class MediaPipeConfig(
    @field:NotBlank
    var url: String = "",
)
