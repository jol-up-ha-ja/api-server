package com.balancemania.api.s3.application

import com.balancemania.api.auth.model.AuthUser
import com.balancemania.api.s3.model.ImageType
import com.balancemania.api.s3.model.S3PresignedUrlModel
import io.awspring.cloud.s3.ObjectMetadata
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.UUID

@Service
class S3PresignedUrlService(
    @Value("\${spring.cloud.aws.s3.bucket}")
    private val bucket: String,
    private val s3Presigner: S3Presigner,
) {
    companion object {
        /** 5분 */
        const val PRESIGNED_EXP_TIME = 5L
    }

    val logger = KotlinLogging.logger { }

    suspend fun generatePresignedUrl(user: AuthUser, imageType: ImageType): S3PresignedUrlModel {
        val fileName = "${user.uid}/${UUID.randomUUID()}.${imageType.type}"

        val metadata = ObjectMetadata.builder()
            .acl(ObjectCannedACL.PUBLIC_READ)
            .build()

        val putObjectRequest = PutObjectRequest.builder()
            .key(fileName)
            .contentType("image/${imageType.type}")
            .bucket(bucket)
            .metadata(metadata.metadata)
            .build()

        val preSignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(PRESIGNED_EXP_TIME))
            .putObjectRequest(putObjectRequest)
            .build()

        val presignedRequest = s3Presigner.presignPutObject(preSignRequest)

        return S3PresignedUrlModel(
            url = presignedRequest.url().toString(),
            exp = presignedRequest.expiration(),
            key = fileName
        )
    }
}
