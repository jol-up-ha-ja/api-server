package com.balancemania.api.balance.domain

import com.balancemania.api.common.entity.BaseEntity
import jakarta.persistence.*

/** 유저 */
@Entity
@Table(name = "balance")
class Balance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    /** uid */
    val uid: Long,

    /** 정면 어깨 각도 */
    @Column(name = "front_shoulder_angle")
    val frontShoulderAngle: Long,

    /** 정면 골반 각도 */
    @Column(name = "front_pelvis_angle")
    val frontPelvisAngle: Long,

    /** 정면 무릎 각도 */
    @Column(name = "front_knee_angle")
    val frontKneeAngle: Long,

    /** 정면 발목 각도 */
    @Column(name = "front_ankle_angle")
    val frontAnkleAngle: Long,

    /** 측면 목 각도 */
    @Column(name = "side_neck_angle")
    val sideNeckAngle: Long,

    /** 측면 신체 각도 */
    @Column(name = "side_body_angle")
    val sideBodyAngle: Long,

    /** 좌측 무게 */
    @Column(name = "left_weight")
    val leftWeight: Long,

    /** 우측 무게 */
    @Column(name = "right_weight")
    val rightWeight: Long,
) : BaseEntity()
