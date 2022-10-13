package com.ericg.kripto.presentation.ui.sharedComposables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkItem(
    linksPair: Pair<String, List<String>?>,
    onLinkClick: (List<String>) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = (0.75).dp,
                color = colorScheme.outline,
                shape = RoundedCornerShape(100)
            )
            .clip(RoundedCornerShape(100))
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(color = colorScheme.onSurface)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                onLinkClick(linksPair.second!!)
            },
        shape = RoundedCornerShape(100),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 14.dp),
            text = linksPair.first,
            color = colorScheme.onSurface
        )
    }
}
