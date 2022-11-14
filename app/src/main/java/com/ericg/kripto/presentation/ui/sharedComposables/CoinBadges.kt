package com.ericg.kripto.presentation.ui.sharedComposables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCoinBadge(isNew: Boolean) {
    if (isNew) Card(
        modifier = Modifier
            .padding(start = 12.dp),
        colors = CardDefaults.cardColors(colorScheme.primary)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.padding(
                    start = 6.dp,
                    end = 6.dp,
                    top = 2.dp,
                    bottom = 1.dp
                ),
                text = "NEW",
                style = typography.labelSmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyTypeBadge(type: String) {
    Card(
        modifier = Modifier
            .padding(start = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (type.equals("COIN", ignoreCase = true))
                colorScheme.secondary else colorScheme.tertiary
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.padding(
                    start = 6.dp,
                    end = 6.dp,
                    top = 2.dp,
                    bottom = 1.dp
                ),
                text = if (type.equals("COIN", ignoreCase = true)) "COIN" else "TOKEN",
                style = typography.labelSmall
            )
        }
    }
}