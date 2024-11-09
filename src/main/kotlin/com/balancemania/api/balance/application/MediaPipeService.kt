package com.balancemania.api.balance.application

import com.balancemania.api.client.mediapipe.MediaPipeClient
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisRequest
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse
import org.springframework.stereotype.Service

@Service
class MediaPipeService(
    private val mediaPipeClient: MediaPipeClient,
) {
    fun requestImgAnalysis(frontImgKey: String, sideImgKey: String): MediaPipeImgAnalysisResponse {
        return MediaPipeImgAnalysisRequest(
            frontImgKey = frontImgKey,
            sideImgKey = sideImgKey
        ).let { mediaPipeClient.getImgAnalysis(it) }
    }
}
