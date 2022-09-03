package com.ericg.kripto.presentation.ui.sharedComposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.presentation.theme.ColorBadgeBg
import com.ericg.kripto.presentation.theme.ColorBadgeText
import com.ericg.kripto.presentation.theme.ColorPrimary

@Composable
fun IsNewCoinBadge(isNew: Boolean) {
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
            fontSize = 10.sp
        )
    }
}

@Composable
fun CoinTypeBadge(type: String) {
    Box(
        modifier = Modifier
            .padding(start = 12.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(
                if (type.equals("COIN", ignoreCase = true))
                    ColorPrimary.copy(alpha = 0.12F) else ColorBadgeBg
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
            fontSize = 10.sp
        )
    }
}