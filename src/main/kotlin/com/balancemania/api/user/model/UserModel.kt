package com.balancemania.api.user.model

import com.balancemania.api.user.domain.User
import com.balancemania.api.user.domain.vo.Gender
import com.balancemania.api.user.domain.vo.OauthInfo
import com.balancemania.api.user.domain.vo.UserStatusType
import java.time.LocalDate
import java.time.LocalDateTime

data class UserModel(
    /** user id */
    val id: Long = -1,

    /** oauth 정보 */
    val oauthInfo: OauthInfo,

    /** 이름 */
    val name: String,

    /** 성별 */
    val gender: Gender?,

    /** 생년월일 */
    val birth: LocalDate?,

    /** 계정 상태 id */
    val statusType: UserStatusType,

    /** 생성일 */
    val createdAt: LocalDateTime,

    /** 수정일 */
    val modifiedAt: LocalDateTime,
) {
    companion object {
        fun from(user: User): UserModel {
            return UserModel(
                id = user.id,
                oauthInfo = user.oauthInfo,
                name = user.name,
                gender = user.gender,
                birth = user.birth,
                statusType = user.statusType,
                createdAt = user.createdAt,
                modifiedAt = user.modifiedAt
            )
        }
    }
}
