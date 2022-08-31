package com.ericg.kripto.presentation.screen.coin_details.state

data class BottomSheetContentState(
    val linksTitle: String = "",
    val icon: Int? = null,
    val links: List<String> = emptyList()
)
