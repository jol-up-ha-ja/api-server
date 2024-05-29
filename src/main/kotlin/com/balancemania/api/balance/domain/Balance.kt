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
    var frontShoulderAngle: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "front_pelvis_angle")
    var frontPelvisAngle: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "front_knee_angle")
    var frontKneeAngle: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "front_ankle_angle")
    var frontAnkleAngle: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "side_neck_angle")
    var sideNeckAngle: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "side_body_angle")
    var sideBodyAngle: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "left_weight")
    var leftWeight: Long? = null,

    /** 정면 어깨 각도 */
    @Column(name = "right_weight")
    var rightWeight: Long? = null,
) : BaseEntity()
