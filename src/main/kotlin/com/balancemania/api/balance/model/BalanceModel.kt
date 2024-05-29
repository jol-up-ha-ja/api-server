package com.balancemania.api.balance.model

import com.balancemania.api.balance.domain.Balance
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

data class BalanceModel(
    val id: Long,

    /** uid */
    val uid: Long,

    /** 정면 어깨 각도 */
    val frontShoulderAngle: Long?,

    /** 정면 어깨 각도 */
    val frontPelvisAngle: Long?,

    /** 정면 어깨 각도 */
    val frontKneeAngle: Long?,

    /** 정면 어깨 각도 */
    val frontAnkleAngle: Long?,

    /** 정면 어깨 각도 */
    val sideNeckAngle: Long?,

    /** 정면 어깨 각도 */
    val sideBodyAngle: Long?,

    /** 정면 어깨 각도 */
    val leftWeight: Long?,

    /** 정면 어깨 각도 */
    val rightWeight: Long?,

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
                frontPelvisAngle = balance.frontPelvisAngle,
                frontShoulderAngle = balance.frontShoulderAngle,
                frontKneeAngle = balance.frontKneeAngle,
                frontAnkleAngle = balance.frontAnkleAngle,
                sideBodyAngle = balance.sideBodyAngle,
                sideNeckAngle = balance.sideNeckAngle,
                leftWeight = balance.leftWeight,
                rightWeight = balance.rightWeight,
                createdAt = balance.createdAt,
                modifiedAt = balance.modifiedAt,
            )
        }
    }
}
