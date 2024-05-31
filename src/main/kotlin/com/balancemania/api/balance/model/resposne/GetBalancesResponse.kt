package com.balancemania.api.balance.model.resposne

import com.balancemania.api.balance.model.BalanceModel

data class GetBalancesResponse(
    val balances: List<BalanceModel>,
)
