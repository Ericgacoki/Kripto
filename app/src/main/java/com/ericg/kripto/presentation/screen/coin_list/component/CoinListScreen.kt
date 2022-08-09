package com.ericg.kripto.presentation.screen.coin_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.coin_list.viewmodel.CoinListViewModel
import com.ericg.kripto.presentation.screen.destinations.CoinDetailsScreenDestination
import com.ericg.kripto.presentation.theme.ColorDormantBg
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.util.GifImageLoader
import com.ericg.kripto.util.random
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CoinLIstScreen(
    navigator: DestinationsNavigator?,
    coinListViewModel: CoinListViewModel = hiltViewModel()
) {
    val coinListState = coinListViewModel.state.value

    Surface(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        if (coinListState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GifImageLoader(
                    modifier = Modifier.size(250.dp),
                    resource = R.drawable.kripto_loading
                )
            }
        } else if (coinListState.coins.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(coinListState.coins) { index, coin ->
                    CoinItem(
                        name = coin.name,
                        symbol = coin.symbol,
                        rank = coin.rank,
                        isActive = coin.isActive,
                        isNew = coin.isNew,
                        type = coin.type
                    ) {
                        navigator?.navigate(CoinDetailsScreenDestination)
                    }
                    if (index != coinListState.coins.lastIndex) {
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = coinListState.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        // TODO: Use an Event for this
                        coinListViewModel.getCoins()
                    }) {
                        Text(
                            text = "RETRY",
                            color = ColorDormantBg
                        )
                    }
                }
            }
        }
    }
}
