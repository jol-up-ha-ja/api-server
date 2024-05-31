package com.balancemania.api.balance.model.request

data class CreateBalanceRequest (
    val frontImgUrl: String,
    val sideImgUrl: String,
    val leftWeight: Float,
    val rightWeight: Float,
)
