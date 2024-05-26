package com.balancemania.api.auth.model.request

import com.balancemania.api.user.domain.vo.Gender
import java.time.LocalDate

data class OAuthRegisterRequest(
    /** 유저 이름 */
    val name: String,
    /** 유저 성별 */
    val gender: Gender?,
    /** 유저 생년월일 */
    val birth: Int?,
) {
    fun getBirth(): LocalDate? {
        return this.birth?.let { LocalDate.of(it, 1, 1) }
    }
}
