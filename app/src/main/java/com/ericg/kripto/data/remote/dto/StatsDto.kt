package com.ericg.kripto.data.remote.dto


import com.google.gson.annotations.SerializedName

data class StatsDto(
    val subscribers: Int?,
    val contributors: Int?,
    val stars: Int?,
    val followers: Int?
)