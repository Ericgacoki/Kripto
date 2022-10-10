package com.ericg.kripto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ExchangeDto(
    val active: Boolean?,
    @SerializedName("adjusted_rank")
    val adjustedRank: Int?,
    @SerializedName("api_status")
    val apiStatus: Boolean?,
    @SerializedName("confidence_score")
    val confidenceScore: Double?,
    val currencies: Int?,
    val description: String?,
    val fiatDtos: List<FiatDto>?,
    val id: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("links")
    val linksDto: LinksDto?,
    val markets: Int?,
    @SerializedName("markets_data_fetched")
    val marketsDataFetched: Boolean?,
    val message: Any?,
    val name: String?,
    val quotesDto: QuotesDto?,
    @SerializedName("reported_rank")
    val reportedRank: Int?,
    @SerializedName("sessions_per_month")
    val sessionsPerMonth: Long? ,
    @SerializedName("website_status")
    val websiteStatus: Boolean?
)
