package com.ericg.kripto.presentation.ui

import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.destinations.CoinLIstScreenDestination
import com.ericg.kripto.presentation.screen.destinations.Destination
import com.ericg.kripto.presentation.screen.destinations.ExchangesScreenDestination
import com.ericg.kripto.presentation.screen.destinations.PriceConversionScreenDestination

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val destination: Destination
) {

    object CoinList : BottomNavItem(
        title = "Coin",
        icon = R.drawable.ic_coin,
        destination = CoinLIstScreenDestination
    )

    object Exchanges : BottomNavItem(
        title = "Market",
        icon = R.drawable.ic_exchange,
        destination = ExchangesScreenDestination
    )

    object PriceConversion : BottomNavItem(
        title = "Rate",
        icon = R.drawable.ic_convert,
        destination = PriceConversionScreenDestination
    )
}
