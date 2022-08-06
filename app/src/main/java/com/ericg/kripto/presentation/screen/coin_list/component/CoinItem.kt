package com.ericg.kripto.presentation.screen.coin_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import com.ericg.kripto.presentation.theme.*

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
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .background(if (isActive) Color.White else ColorDormantBg)
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
                        text = name,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        color = ColorPrimary,
                        fontSize = 18.sp
                    )

                    if (isNew) Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(ColorBadgeBg)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            text = "NEW",
                            color = ColorBadgeText,
                            fontSize = 8.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (type.equals("COIN", ignoreCase = true))
                                    ColorPrimary.copy(alpha = 0.24F) else ColorBadgeBg
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                start = 6.dp,
                                end = 6.dp,
                                top = 2.dp,
                                bottom = 1.dp
                            ),
                            text = if (type.equals("COIN", ignoreCase = true)) "COIN" else "TOKEN",
                            color = if (type.equals(
                                    "COIN",
                                    ignoreCase = true
                                )
                            ) ColorPrimary else ColorBadgeText,
                            fontSize = 8.sp
                        )
                    }
                }

                Text(
                    text = symbol,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    color = ColorPrimary.copy(alpha = .56F),
                    fontSize = 12.sp
                )
            }
        }

        if (!isActive) Text(
            modifier = Modifier.padding(end = 12.dp),
            text = "Dormant",
            color = ColorDormant
        )
    }
}

@Composable
private fun RankBadge(rank: Int, name: String) {
    Box {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(ColorPrimary.copy(alpha = 0.24F)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = ColorPrimary,
                fontSize = 26.sp
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 32.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp,  top = 1.dp),
                text = rank.toString(),
                fontWeight = FontWeight.Light,
                color = ColorPrimary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun CoinItemPrev() {
    Box(modifier = Modifier.fillMaxSize()) {
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