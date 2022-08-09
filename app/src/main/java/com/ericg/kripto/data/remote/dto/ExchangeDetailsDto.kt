package com.ericg.kripto.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ExchangeDetailsDto(
    val id: String,
    val name: String,
    val description: String,
    val active: Boolean,
    @SerializedName("website_status")
    val websiteStatus: Boolean,
    @SerializedName("api_status")
    val apiStatus: Boolean,
    val message: Any?,
    val linksDto: LinksDto,
    @SerializedName("markets_data_fetched")
    val marketsDataFetched: Boolean,
    @SerializedName("adjusted_rank")
    val adjustedRank: Int,
    @SerializedName("reported_rank")
    val reportedRank: Int,
    val currencies: Int,
    val markets: Int,
    val fiats: List<Any?>,
    val quotesDto: QuotesDto,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("img_rev")
    val imgRev: Int,
    @SerializedName("confidence_score")
    val confidenceScore: Int
)
