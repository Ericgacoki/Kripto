package com.ericg.kripto.domain.model

data class PriceConversion(
    val baseCurrencyId: String,
    val baseCurrencyName: String,
    val basePriceLastUpdated: String,
    val quoteCurrencyId: String,
    val quoteCurrencyName: String,
    val quotePriceLastUpdated: String,
    val amount: Double,
    val price: Double
)
