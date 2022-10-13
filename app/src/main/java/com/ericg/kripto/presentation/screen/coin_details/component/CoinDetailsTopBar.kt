package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.presentation.theme.ColorBadgeBg
import com.ericg.kripto.presentation.theme.ColorBadgeText
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.presentation.ui.sharedComposables.CoinTypeBadge
import com.ericg.kripto.presentation.ui.sharedComposables.NewCoinBadge

@Composable
fun CoinDetailsTopBar(
    name: String,
    symbol: String,
    rank: Int,
    type: String,
    isNew: Boolean
) {
    Column(
        modifier = Modifier
            .padding(start = 12.dp, top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = trimText(name, 16),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = ColorPrimary
            )

            LazyRow(modifier = Modifier.fillMaxWidth(),
                content = {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(ColorBadgeBg)
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                text = rank.toString(),
                                color = ColorBadgeText,
                                fontSize = 10.sp
                            )
                        }
                    }
                    item { NewCoinBadge(isNew = isNew) }
                    item { CoinTypeBadge(type = type) }
                })
        }

        Text(
            text = symbol,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            color = ColorPrimary.copy(alpha = .56F),
            fontSize = 14.sp
        )
    }
}

internal fun trimText(text: String, maxLength: Int): String {
    return when (text.length) {
        in 0..maxLength -> text
        in maxLength..Int.MAX_VALUE -> text.substring(startIndex = 0, endIndex = maxLength - 1) + "..."
        else -> text
    }
}
