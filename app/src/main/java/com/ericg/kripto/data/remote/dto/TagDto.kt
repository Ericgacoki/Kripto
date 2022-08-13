package com.ericg.kripto.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TagDto(
    val id: String,
    val name: String,
    @SerializedName("coin_counter")
    val coinCounter: Int,
    @SerializedName("ico_counter")
    val icoCounter: Int
)
