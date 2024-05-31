package com.balancemania.api.balance.application

import com.balancemania.api.client.mediapipe.MediaPipeClient
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse
import com.balancemania.api.extension.withMDCContext
import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Service

@Service
class MediaPipeService(
    private val mediaPipeClient: MediaPipeClient,
) {
    suspend fun requestImgAnalysis(frontImgUrl: String, sideImgUrl: String): MediaPipeImgAnalysisResponse {
        return withMDCContext(Dispatchers.IO) {
            mediaPipeClient.getImgAnalysis(frontImgUrl, sideImgUrl)
        }
    }
}
