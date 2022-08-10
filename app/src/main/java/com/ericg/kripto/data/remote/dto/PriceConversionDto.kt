package com.ericg.kripto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PriceConversionDto(
    @SerializedName("base_currency_id")
    val baseCurrencyId: String,
    @SerializedName("base_currency_name")
    val baseCurrencyName: String,
    @SerializedName("base_price_last_updated")
    val basePriceLastUpdated: String,
    @SerializedName("quote_currency_id")
    val quoteCurrencyId: String,
    @SerializedName("quote_currency_name")
    val quoteCurrencyName: String,
    @SerializedName("quote_price_last_updated")
    val quotePriceLastUpdated: String,
    val amount: Double,
    val price: Double
)
