package com.ericg.kripto.presentation.screen.coin_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.coin_list.viewmodel.CoinListViewModel
import com.ericg.kripto.presentation.screen.destinations.CoinDetailsScreenDestination
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.presentation.ui.sharedComposables.AppTopBar
import com.ericg.kripto.presentation.ui.sharedComposables.RetryButton
import com.ericg.kripto.util.GifImageLoader
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CoinLIstScreen(
    navigator: DestinationsNavigator?,
    coinListViewModel: CoinListViewModel = hiltViewModel()
) {

    val coinListState = coinListViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Coins",
                showSearchBar = true,
                onSearchParamChange = { newParam ->
                    // TODO: Initiate a search event
                }
            )
        }
    ) {
        val unUsedPadding = it

        if (coinListState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GifImageLoader(
                    modifier = Modifier.size(250.dp),
                    resource = R.drawable.kripto_loading
                )
            }
        } else if (coinListState.value.coins.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(coinListState.value.coins) { index, coin ->
                    CoinItem(
                        name = coin.name,
                        symbol = coin.symbol,
                        rank = coin.rank,
                        isActive = coin.isActive,
                        isNew = coin.isNew,
                        type = coin.type
                    ) {
                        navigator?.navigate(
                            CoinDetailsScreenDestination(
                                id = coin.id,
                                name = coin.name,
                                symbol = coin.symbol,
                                rank = coin.rank,
                                type = coin.type,
                                isNew = coin.isNew
                            )
                        )
                    }
                    if (index != coinListState.value.coins.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .padding(start = 52.dp)
                                .height((.5).dp)
                                .fillMaxWidth(1F)
                                .background(color = ColorPrimary.copy(alpha = 0.24F))
                        )
                    } else {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        } else {
            RetryButton(
                error = coinListState.value.error,
                onRetryEvent = {
                    // TODO: Use UI Event here
                    coinListViewModel.getCoins()
                }
            )
        }
    }
}
