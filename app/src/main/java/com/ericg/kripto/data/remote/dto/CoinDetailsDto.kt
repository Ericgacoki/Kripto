package com.ericg.kripto.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CoinDetailsDto(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val rank: Int?,
    @SerializedName("is_new")
    val isNew: Boolean?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    val type: String?,
    @SerializedName("tags")
    val tagDtos: List<TagDto>?,
    val team: List<TeamMemberDto>?,
    val description: String?,
    val message: String?,
    @SerializedName("open_source")
    val openSource: Boolean?,
    @SerializedName("started_at")
    val startedAt: String?,
    @SerializedName("development_status")
    val developmentStatus: String?,
    @SerializedName("hardware_wallet")
    val hardwareWallet: Boolean?,
    @SerializedName("proof_type")
    val proofType: String?,
    @SerializedName("org_structure")
    val orgStructure: String?,
    @SerializedName("hash_algorithm")
    val hashAlgorithm: String?,
    val links: CoinDetailLinkDto?,
    @SerializedName("links_extended")
    val linksExtendedDto: List<LinksExtendedDto>?,
    val whitepaperDto: WhitePaperDto?,
    @SerializedName("first_data_at")
    val firstDataAt: String?,
    @SerializedName("last_data_at")
    val lastDataAt: String?
)
