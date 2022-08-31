package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.presentation.screen.coin_details.state.BottomSheetContentState
import com.ericg.kripto.presentation.theme.ColorLink
import com.ericg.kripto.presentation.theme.ColorPrimary

@Composable
fun BottomSheetContent(
    bottomSheetContentState: BottomSheetContentState,
    onLinkClick: (link: String) -> Unit,
) {
    Box {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(ColorPrimary)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            bottomSheetContentState.links.forEachIndexed { index, link ->
                item {
                    val interaction = remember { MutableInteractionSource() }
                    Text(
                        modifier = Modifier
                            .indication(
                                interactionSource = interaction,
                                indication = rememberRipple(color = ColorLink)
                            )
                            .clickable(
                                interactionSource = interaction,
                                indication = null
                            ) {
                                onLinkClick(link)
                            }
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        text = "${index + 1}.  $link",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = ColorLink
                    )
                    if (index != bottomSheetContentState.links.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .height((.5).dp)
                                .fillMaxWidth(1F)
                                .background(color = Color.White.copy(alpha = 0.24F))
                        )
                    }
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .width(36.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White.copy(alpha = .88F))
                    .padding(12.dp)
            )
        }
    }
}
