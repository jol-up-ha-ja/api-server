package com.balancemania.api.balance.model

import com.balancemania.api.balance.domain.Balance
import com.balancemania.api.extension.toRealNumber
import java.time.LocalDateTime

data class BalanceModel(
    val id: Long,

    /** uid */
    val uid: Long,

    /** 정면 어깨 각도 */
    val frontShoulderAngle: Float,

    /** 정면 골반 각도 */
    val frontPelvisAngle: Float,

    /** 정면 무릎 각도 */
    val frontKneeAngle: Float,

    /** 정면 발목 각도 */
    val frontAnkleAngle: Float,

    /** 측면 목 각도 */
    val sideNeckAngle: Float,

    /** 측면 신체 각도 */
    val sideBodyAngle: Float,

    /** 좌측 무게 */
    val leftWeight: Float,

    /** 우측 무게 */
    val rightWeight: Float,

    /** 생성일 */
    val createdAt: LocalDateTime,

    /** 수정일 */
    val modifiedAt: LocalDateTime,
) {
    companion object {
        fun from(balance: Balance): BalanceModel {
            return BalanceModel(
                id = balance.id,
                uid = balance.uid,
                frontPelvisAngle = balance.frontPelvisAngle.toRealNumber(),
                frontShoulderAngle = balance.frontShoulderAngle.toRealNumber(),
                frontKneeAngle = balance.frontKneeAngle.toRealNumber(),
                frontAnkleAngle = balance.frontAnkleAngle.toRealNumber(),
                sideBodyAngle = balance.sideBodyAngle.toRealNumber(),
                sideNeckAngle = balance.sideNeckAngle.toRealNumber(),
                leftWeight = balance.leftWeight.toRealNumber(),
                rightWeight = balance.rightWeight.toRealNumber(),
                createdAt = balance.createdAt,
                modifiedAt = balance.modifiedAt
            )
        }
    }
}
