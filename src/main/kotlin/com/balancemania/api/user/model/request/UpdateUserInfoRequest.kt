package com.balancemania.api.user.model.request

import com.balancemania.api.user.domain.vo.Gender
import java.time.LocalDate

data class UpdateUserInfoRequest(
    /** 이름 */
    val name: String,

    /** 성별 */
    val gender: Gender? = null,

    /** 생년월일 */
    val birth: LocalDate? = null,
)
