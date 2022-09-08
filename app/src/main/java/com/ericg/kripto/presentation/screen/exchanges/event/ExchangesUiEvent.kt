package com.ericg.kripto.presentation.screen.exchanges.event

sealed class ExchangesUiEvent {
    object GetExchanges: ExchangesUiEvent()
    data class SearchExchange(val searchParams: String): ExchangesUiEvent()
}
