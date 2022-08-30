package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ericg.kripto.presentation.theme.ColorLink

@Composable
fun LinkItem(
    linksPair: Pair<String, List<String>?>,
    onNameClick: (List<String>) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val rippleColor = ColorLink

    Box(
        modifier = Modifier
            .padding(6.dp)
            .border(
                width = (0.75).dp,
                color = ColorLink,
                shape = RoundedCornerShape(100)
            )
            .clip(shape = RoundedCornerShape(100))
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(color = rippleColor)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                onNameClick(linksPair.second!!)
            }
    ) {
        Text(
            modifier = Modifier.padding(top = 6.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
            text = linksPair.first,
            color = ColorLink
        )
    }
}
