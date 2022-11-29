package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TagItem(name: String) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(2.dp))
            .clip(shape = CutCornerShape(topStart = 12.dp))
            .background(colorScheme.tertiary.copy(alpha = .12F))
    ) {
        Text(
            modifier = Modifier.padding(top = 6.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
            text = name,
            color = colorScheme.tertiary
        )
    }
}
