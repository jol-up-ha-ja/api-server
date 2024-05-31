package com.balancemania.api.extension

import kotlin.math.roundToLong

fun Float.toInteger(): Long = (this * 10000).roundToLong()

fun Long.toRealNumber(): Float = this / 10000f
