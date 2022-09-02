package com.ericg.kripto.presentation.ui.sharedComposables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ericg.kripto.presentation.theme.ColorDormantBg

@Composable
fun RetryButton(error: String, onRetryEvent: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(shape = RoundedCornerShape(100),
                onClick = {
                    onRetryEvent()
                }) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "RETRY",
                    color = ColorDormantBg
                )
            }
        }
    }
}
