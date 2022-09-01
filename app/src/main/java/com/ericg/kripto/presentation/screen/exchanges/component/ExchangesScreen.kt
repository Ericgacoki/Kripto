package com.ericg.kripto.presentation.screen.exchanges.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ericg.kripto.presentation.ui.sharedComposables.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ExchangesScreen() {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Exchanges",
                showSearchBar = true,
                initialValue = "", // TODO: Hoist this state in VM
                onSearchParamChange = { newParam ->
                    // TODO: Initiate a search event
                }
            )
        }
    ) {
        val unUsedPd = it
    }
}