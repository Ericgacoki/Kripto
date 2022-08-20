package com.ericg.kripto.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CoinDetailLinkDto(
    val explorer: List<String>?,
    val facebook: List<String>?,
    val reddit: List<String>?,
    @SerializedName("source_code")
    val sourceCode: List<String>?,
    val website: List<String>?,
    val youtube: List<String>?
)
