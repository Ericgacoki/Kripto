package com.ericg.kripto.presentation.screen.price_conversion.event

sealed class ConversionUiEvent {
    data class OnConvert(
        val amount: Double,
        val baseCurrencyId: String,
        val quoteCurrencyId: String
    ) : ConversionUiEvent()

    object ResetState : ConversionUiEvent()
}
