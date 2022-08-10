package com.ericg.kripto.data.remote.dto


data class LinksExtendedDto(
    val url: String,
    val type: String,
    val statsDto: StatsDto?
)