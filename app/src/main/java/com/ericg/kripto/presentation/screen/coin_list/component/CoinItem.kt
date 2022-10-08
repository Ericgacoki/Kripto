package com.ericg.kripto.presentation.screen.coin_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.presentation.ui.sharedComposables.CoinTypeBadge
import com.ericg.kripto.presentation.ui.sharedComposables.IsNewCoinBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinItem(
    name: String,
    symbol: String,
    rank: Int,
    isActive:
    Boolean,
    isNew: Boolean,
    type: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.background(
            if (isActive) Color.Unspecified else colorScheme.primary.copy(
                .24F
            )
        )
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                RankBadge(rank = rank, name = name)

                Column(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isActive) name else {
                                when (name.length) {
                                    in 0..12 -> name
                                    else -> name.substring(0..11) + "..."
                                }
                            },
                            textAlign = TextAlign.Start,
                            style = typography.titleMedium
                        )
                        IsNewCoinBadge(isNew) // FIXME: Colors
                        CoinTypeBadge(type = type)
                    }

                    Text(
                        text = symbol,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = Color.Unspecified.copy(alpha = .56F),
                        fontSize = 12.sp,
                        style = typography.titleSmall
                    )
                }
            }

            if (!isActive) Text(
                modifier = Modifier.padding(end = 12.dp),
                text = "Dormant",
                color = Color.Unspecified.copy(alpha = .56F),
                style = typography.labelMedium
            )
        }
    }

}

@Composable
private fun RankBadge(rank: Int, name: String) {
    Box {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(colorScheme.primary.copy(alpha = 0.24F)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                style = typography.headlineSmall
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 32.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 1.dp),
                text = rank.toString(),
                fontWeight = FontWeight.Light,
                color = colorScheme.onPrimary,
                style = typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
fun CoinItemPrev() {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface() {
            LazyColumn {
                items(10) { index ->
                    CoinItem(
                        rank = 123,
                        name = "Bitcoin",
                        symbol = "BTC",
                        type = if (index % 3 != 0) "Coin" else "Currency",
                        isActive = (index + 1) % 3 != 0,
                        isNew = (index + 2) % 3 == 0
                    ) { }
                }
            }
        }
    }
}
