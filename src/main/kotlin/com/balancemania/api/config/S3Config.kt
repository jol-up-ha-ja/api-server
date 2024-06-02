package com.balancemania.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class S3Config {
    @Bean
    fun s3Presigner(): S3Presigner {
        return S3Presigner.builder()
            .credentialsProvider(DefaultCredentialsProvider.create())
            .region(Region.AP_NORTHEAST_2)
            .build()
    }
}
