package com.balancemania.api.client.mediapipe.model

data class MediaPipeImgAnalysisResponse (
    /** 정면 어깨 각도 */
    val frontShoulderAngle: Long,

    /** 정면 골반 각도 */
    val frontPelvisAngle: Long,

    /** 정면 무릎 각도 */
    val frontKneeAngle: Long,

    /** 정면 발목 각도 */
    val frontAnkleAngle: Long,

    /** 측면 목 각도 */
    val sideNeckAngle: Long,

    /** 측면 신체 각도 */
    val sideBodyAngle: Long,
)
