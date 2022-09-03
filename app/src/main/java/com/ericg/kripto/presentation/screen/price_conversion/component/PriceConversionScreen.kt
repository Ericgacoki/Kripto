package com.ericg.kripto.presentation.screen.price_conversion.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ericg.kripto.presentation.ui.sharedComposables.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun PriceConversionScreen() {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Conversion",
                initialValue = "", // ignore
                onSearchParamChange = {
                    /** NOTE
                     * Ignore because the search bar is not shown
                     */
                }
            )
        }
    ) {
        val unUsedPd = it
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Come back tomorrow... \uD83E\uDD74")
        }
    }
}
