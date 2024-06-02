package com.balancemania.api.client.mediapipe.model

data class MediaPipeImgAnalysisRequest(
    /** 정면 사진 키 */
    val frontImgKey: String,
    /** 측면 사진 키 */
    val sideImgKey: String,
)
