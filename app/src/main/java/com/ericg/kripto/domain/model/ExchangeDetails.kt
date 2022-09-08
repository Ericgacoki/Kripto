package com.ericg.kripto.domain.model

data class ExchangeDetails(
    val id: String,
    val name: String,
    val description: String,
    val active: Boolean,
    val websiteStatus: Boolean,
    val apiStatus: Boolean,
    val message: Any?,
    val links: Links?,
    val marketsDataFetched: Boolean,
    val adjustedRank: Int,
    val reportedRank: Int,
    val currencies: Int,
    val markets: Int,
    val fiats: List<Any?>,
    val quotes: Quotes,
    val lastUpdated: String,
    val imgRev: Int,
    val confidenceScore: Int
)
