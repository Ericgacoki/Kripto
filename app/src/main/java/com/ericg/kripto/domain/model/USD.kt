package com.ericg.kripto.domain.model

data class USD(
    val reportedVolume24h: Int?,
    val adjustedVolume24h: Int?,
    val reportedVolume7d: Double?,
    val adjustedVolume7d: Double?,
    val reportedVolume30d: Double?,
    val adjustedVolume30d: Double?
)
