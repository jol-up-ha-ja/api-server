package com.balancemania.api.balance.application

import com.balancemania.api.client.mediapipe.MediaPipeClient
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisRequest
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse
import com.balancemania.api.extension.withMDCContext
import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Service

@Service
class MediaPipeService(
    private val mediaPipeClient: MediaPipeClient,
) {
    suspend fun requestImgAnalysis(frontImgKey: String, sideImgKey: String): MediaPipeImgAnalysisResponse {
        val body = MediaPipeImgAnalysisRequest(
            frontImgKey = frontImgKey,
            sideImgKey = sideImgKey
        )

        return withMDCContext(Dispatchers.IO) {
            mediaPipeClient.getImgAnalysis(body)
        }
    }
}
