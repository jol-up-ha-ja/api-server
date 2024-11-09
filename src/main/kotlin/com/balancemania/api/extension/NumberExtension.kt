package com.balancemania.api.extension

import kotlin.math.roundToLong

fun Double.toInteger(): Long = (this * 10000).roundToLong()

fun Long.toRealNumber(): Double = (this / 10000).toDouble()
