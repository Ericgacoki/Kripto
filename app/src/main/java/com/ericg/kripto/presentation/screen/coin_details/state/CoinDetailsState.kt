package com.ericg.kripto.presentation.screen.coin_details.state

import com.ericg.kripto.domain.model.CoinDetails

data class CoinDetailsState(
    val isLoading: Boolean = false,
    val coinDetails: CoinDetails? = null,
    val error: String = ""
)
