package com.ericg.kripto.presentation.ui.sharedComposables

import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.destinations.CoinLIstScreenDestination
import com.ericg.kripto.presentation.screen.destinations.Destination
import com.ericg.kripto.presentation.screen.destinations.ExchangesScreenDestination
import com.ericg.kripto.presentation.screen.destinations.PriceConversionScreenDestination

sealed class BottomNavItem(
    val icon: Int,
    val destination: Destination,
    val title: String
) {

    object CoinList : BottomNavItem(
        icon = R.drawable.ic_coin,
        destination = CoinLIstScreenDestination,
        title = "Coins"
    )

    object Exchanges : BottomNavItem(
        icon = R.drawable.ic_exchange,
        destination = ExchangesScreenDestination,
        title = "Exchanges"
    )

    object PriceConversion : BottomNavItem(
        icon = R.drawable.ic_convert,
        destination = PriceConversionScreenDestination,
        title = "Conversion"
    )
}
