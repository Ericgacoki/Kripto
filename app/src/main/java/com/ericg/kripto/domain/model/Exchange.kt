package com.ericg.kripto.domain.model

data class Exchange(
    val active: Boolean,
    val adjustedRank: Int,
    val apiStatus: Boolean,
    val confidenceScore: Double,
    val currencies: Int,
    val description: String,
    val fiats: List<Fiat>,
    val id: String,
    val lastUpdated: String,
    val links: Links,
    val markets: Int,
    val marketsDataFetched: Boolean,
    val message: Any?,
    val name: String,
    val quotes: Quotes,
    val reportedRank: Int,
    val sessionsPerMonth: Int,
    val websiteStatus: Boolean
)
