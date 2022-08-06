package com.ericg.kripto.data.remote.dto


import com.google.gson.annotations.SerializedName

data class QuotesDto(
    @SerializedName("USD")
    val usdDto: USDDto
)