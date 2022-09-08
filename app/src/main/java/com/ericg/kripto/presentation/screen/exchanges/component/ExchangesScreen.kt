package com.ericg.kripto.presentation.screen.exchanges.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.exchanges.event.ExchangesUiEvent
import com.ericg.kripto.presentation.screen.exchanges.viewmodel.ExchangesViewModel
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.presentation.ui.sharedComposables.AppTopBar
import com.ericg.kripto.presentation.ui.sharedComposables.NoMatchFound
import com.ericg.kripto.presentation.ui.sharedComposables.RetryButton
import com.ericg.kripto.util.GifImageLoader
import com.ramcosta.composedestinations.annotation.Destination
import timber.log.Timber

@Destination
@Composable
fun ExchangesScreen(
    exchangesViewModel: ExchangesViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val exchangesState = exchangesViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            AppTopBar(
                title = "Exchanges",
                showSearchBar = true,
                initialValue = exchangesViewModel.searchParams,
                onSearchParamChange = { newParam ->
                    exchangesViewModel.onEvent(ExchangesUiEvent.SearchExchange(newParam))
                }
            )
        }
    ) {
        val iDoNotNeedThisPaddingValues = it

        if (exchangesState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GifImageLoader(
                    modifier = Modifier.size(250.dp),
                    resource = R.raw.kripto_loading
                )
            }
        } else if (exchangesState.value.exchanges.isNotEmpty()) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(exchangesState.value.exchanges) { index, exchange ->
                    ExchangeItem(exchange)
                    if (index != exchangesState.value.exchanges.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .height((.5).dp)
                                .fillMaxWidth()
                                .background(color = ColorPrimary.copy(alpha = 0.32F))
                        )
                    } else {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        } else if (exchangesState.value.error.isNotEmpty()) {
            RetryButton(
                error = exchangesState.value.error,
                onRetryEvent = {
                    exchangesViewModel.onEvent(ExchangesUiEvent.GetExchanges)
                }
            )
        } else {
            NoMatchFound(lottie = R.raw.no_match_found_lottie)
        }
    }
}
