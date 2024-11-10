package com.balancemania.api.client.mediapipe

import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisRequest
import com.balancemania.api.client.mediapipe.model.MediaPipeImgAnalysisResponse
import com.balancemania.api.config.MediaPipeConfig
import com.balancemania.api.extension.awaitWithMdc
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.reactive.function.client.WebClient

interface MediaPipeClient {
    fun getImgAnalysis(
        body: MediaPipeImgAnalysisRequest,
    ): MediaPipeImgAnalysisResponse

    class Default(
        private val webClient: WebClient,
        private val mediaPipeConfig: MediaPipeConfig,
    ) : MediaPipeClient {
        private val logger = KotlinLogging.logger { }

        override fun getImgAnalysis(body: MediaPipeImgAnalysisRequest): MediaPipeImgAnalysisResponse {
            return webClient.post()
                .uri(mediaPipeConfig.url)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(MediaPipeImgAnalysisResponse::class.java)
                .awaitWithMdc()
        }
    }
}
