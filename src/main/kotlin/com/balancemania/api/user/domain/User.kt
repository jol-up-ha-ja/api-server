package com.balancemania.api.user.domain

import com.balancemania.api.common.entity.BaseEntity
import com.balancemania.api.user.domain.vo.Gender
import com.balancemania.api.user.domain.vo.OauthInfo
import com.balancemania.api.user.domain.vo.UserStatusType
import jakarta.persistence.*
import java.time.LocalDate

/** 유저 */
@Entity
@Table(name = "user")
class User(
    /** user id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    /** oauth 정보 */
    @Embedded
    var oauthInfo: OauthInfo,

    /** 이름 */
    var name: String,

    /** 성별 */
    @Enumerated(EnumType.ORDINAL)
    var gender: Gender? = null,

    /** 생년월일 */
    var birth: LocalDate? = null,

    /** 계정 상태 id */
    @Column(name = "status_type")
    var statusType: UserStatusType,
) : BaseEntity()
