package com.balancemania.api.client.mediapipe

import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse
import com.balancemania.api.config.MediaPipeConfig
import com.balancemania.api.exception.ErrorCode
import com.balancemania.api.exception.FailToExecuteException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.reactive.function.client.WebClient

class SuspendableMediaPipeClient(
    private val webClient: WebClient,
    private val mediaPipeConfig: MediaPipeConfig,
) : MediaPipeClient {
    private val logger = KotlinLogging.logger { }

    override suspend fun getImgAnalysis(frontImgUrl: String, sideImgUrl: String): MediaPipeImgAnalysisResponse {
        return webClient.post()
            .uri(mediaPipeConfig.url)
            .retrieve()
            .bodyToMono(MediaPipeImgAnalysisResponse::class.java)
            .block() ?: throw FailToExecuteException(ErrorCode.EXTERNAL_SERVER_ERROR)
    }
}
