package com.balancemania.api.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

object Zone {
    val KST: ZoneId = ZoneId.of("Asia/Seoul")
    val UTC: ZoneId = ZoneId.of("UTC")
}

fun LocalDateTime.toInstant(): Instant {
    return this.toInstant(ZoneOffset.of("+09:00"))
}

fun Instant.toCurrentZone(): Instant {
    return this.plusSeconds(60 * 60 * 9)
}
