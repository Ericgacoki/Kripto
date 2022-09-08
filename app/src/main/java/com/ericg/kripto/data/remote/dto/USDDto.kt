package com.ericg.kripto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class USDDto(
    @SerializedName("reported_volume_24h")
    val reportedVolume24h: Int,
    @SerializedName("adjusted_volume_24h")
    val adjustedVolume24h: Int,
    @SerializedName("reported_volume_7d")
    val reportedVolume7d: Double,
    @SerializedName("adjusted_volume_7d")
    val adjustedVolume7d: Double,
    @SerializedName("reported_volume_30d")
    val reportedVolume30d: Double,
    @SerializedName("adjusted_volume_30d")
    val adjustedVolume30d: Double
)