package com.balancemania.api.s3.model

import java.time.Instant

data class S3PresignedUrlModel(
    /** presinged url */
    val url: String,
    /** expiration time */
    val exp: Instant,
    /** presinged url */
    val key: String,
)
