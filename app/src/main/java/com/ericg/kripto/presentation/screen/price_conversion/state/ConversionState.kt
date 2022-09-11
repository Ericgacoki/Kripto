package com.ericg.kripto.presentation.screen.price_conversion.state

import com.ericg.kripto.domain.model.PriceConversion

sealed class ConversionState {
    object Loading: ConversionState()
    object NotLoading: ConversionState()
    data class Error(val message: String): ConversionState()
    data class Success(val data: PriceConversion): ConversionState()
}
