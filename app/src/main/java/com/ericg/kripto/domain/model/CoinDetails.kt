package com.ericg.kripto.domain.model

data class CoinDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String,
    val tags: List<Tag>,
    val team: List<TeamMember>,
    val description: String,
    val message: String,
    val openSource: Boolean,
    val startedAt: String,
    val developmentStatus: String,
    val hardwareWallet: Boolean,
    val proofType: String,
    val orgStructure: String,
    val hashAlgorithm: String,
    val links: CoinDetailLink,
    val linksExtended: List<LinksExtended>,
    val whitepaper: Whitepaper,
    val firstDataAt: String,
    val lastDataAt: String
)
