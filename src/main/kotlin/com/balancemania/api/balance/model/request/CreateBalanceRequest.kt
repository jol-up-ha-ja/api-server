package com.balancemania.api.balance.model.request

data class CreateBalanceRequest(
    val frontImgKey: String,
    val sideImgKey: String,
    val leftWeight: Float,
    val rightWeight: Float,
)
