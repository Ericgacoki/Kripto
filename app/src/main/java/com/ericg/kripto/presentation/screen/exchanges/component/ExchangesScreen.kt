package com.ericg.kripto.presentation.screen.exchanges.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.exchanges.event.ExchangesUiEvent
import com.ericg.kripto.presentation.screen.exchanges.viewmodel.ExchangesViewModel
import com.ericg.kripto.presentation.ui.sharedComposables.AppTopBar
import com.ericg.kripto.presentation.ui.sharedComposables.NoMatchFound
import com.ericg.kripto.presentation.ui.sharedComposables.RetryButton
import com.ericg.kripto.util.GifImageLoader
import com.ericg.kripto.util.ext.isScrollingUp
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ExchangesScreen(
    exchangesViewModel: ExchangesViewModel = hiltViewModel()
) {
    val lazyColumnState = rememberLazyListState()
    val exchangesState = exchangesViewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Exchanges",
                showSearchBar = lazyColumnState.isScrollingUp(),
                initialValue = exchangesViewModel.searchParams,
                onSearchParamChange = { newParam ->
                    exchangesViewModel.onEvent(ExchangesUiEvent.SearchExchange(newParam))
                }
            )
        }
    ) {
        if (exchangesState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GifImageLoader(
                    modifier = Modifier.size(250.dp),
                    resource = R.raw.kripto_loading
                )
            }
        } else if (exchangesState.value.exchanges.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), state = lazyColumnState
            ) {
                itemsIndexed(exchangesState.value.exchanges) { index, exchange ->
                    ExchangeItem(exchange)

                    if (index != exchangesState.value.exchanges.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .height((.5).dp)
                                .fillMaxWidth()
                                .background(color = colorScheme.onSurface.copy(alpha = 0.32F))
                        )
                    } else {
                        Spacer(modifier = Modifier.height(150.dp))
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
