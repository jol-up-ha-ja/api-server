package com.balancemania.api.client.mediapipe

import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse

interface MediaPipeClient {
    suspend fun getImgAnalysis(
        frontImgUrl: String,
        sideImgUrl: String,
    ): MediaPipeImgAnalysisResponse
}
