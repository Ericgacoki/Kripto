package com.ericg.kripto.presentation.screen.exchanges.state

import com.ericg.kripto.domain.model.Exchange

data class ExchangesState(
    val isLoading: Boolean = false,
    val exchanges: List<Exchange> = emptyList(),
    val error: String = ""
)
