package com.ericg.kripto.presentation.screen.coin_list.state

import com.ericg.kripto.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
