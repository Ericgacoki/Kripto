package com.ericg.kripto.presentation.screen.coin_list.event

sealed class CoinListUiEvent{
    object GetCoins: CoinListUiEvent()
    data class SearchCoin(val searchParams: String): CoinListUiEvent()
}
