package com.balancemania.api.s3.application

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.extension.toCurrentZone
import com.balancemania.api.s3.model.ImageType
import com.balancemania.api.s3.model.response.S3PresignedUrlResponse
import org.springframework.stereotype.Service

@Service
class S3Facade(
    private val s3PresignedUrlService: S3PresignedUrlService,
) {
    suspend fun getPresignedUrl(user: AuthUser, imgType: ImageType): S3PresignedUrlResponse {
        return s3PresignedUrlService.generatePresignedUrl(user, imgType)
            .let { model ->
                S3PresignedUrlResponse(
                    url = model.url,
                    exp = model.exp.toCurrentZone(),
                    key = model.key
                )
            }
    }
}
