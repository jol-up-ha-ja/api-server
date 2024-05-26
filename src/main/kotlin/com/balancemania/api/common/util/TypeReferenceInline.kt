package com.balancemania.api.common.util

import com.fasterxml.jackson.core.type.TypeReference

inline fun <reified T> toTypeReference(): TypeReference<T> {
    return object : TypeReference<T>() {}
}
