package com.balancemania.api.s3.model.response

import java.time.Instant

class S3PresignedUrlResponse(
    /** presinged url */
    val url: String,
    /** expiration time */
    val exp: Instant,
    /** presinged url */
    val key: String,
)
