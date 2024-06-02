package com.balancemania.api.client.mediapipe

import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisRequest
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse

interface MediaPipeClient {
    suspend fun getImgAnalysis(
        body: MediaPipeImgAnalysisRequest
    ): MediaPipeImgAnalysisResponse
}
