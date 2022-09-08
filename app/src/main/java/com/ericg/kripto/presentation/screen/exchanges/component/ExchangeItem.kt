package com.ericg.kripto.presentation.screen.exchanges.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.domain.model.Exchange
import com.ericg.kripto.presentation.theme.ColorBadgeBg
import com.ericg.kripto.presentation.theme.ColorBadgeText
import com.ericg.kripto.presentation.theme.ColorDormantBg
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.presentation.ui.sharedComposables.LinkItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExchangeItem(exchange: Exchange) {

    val uriHandler = LocalUriHandler.current
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (exchange.active == true) Color.White else ColorDormantBg)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (exchange.name?.length) {
                        in 0..16 -> exchange.name ?: "Anonymous"
                        else -> exchange.name?.substring(0..15) + "..."
                    },
                    fontSize = 18.sp,
                    color = ColorPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .clip(shape = RoundedCornerShape(100))
                        .background(
                            if (exchange.active == true) ColorBadgeBg else ColorPrimary.copy(
                                alpha = .32F
                            )
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (exchange.active == true) "Active" else "Dormant",
                        fontSize = 12.sp,
                        color = if (exchange.active == true) ColorBadgeText else Color.White
                    )
                }
            }


            IconButton(onClick = { expanded = expanded.not() }) {
                if (expanded)
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "")
                else
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }

        AnimatedVisibility(visible = expanded) {
            Text(
                text = exchange.description?.ifEmpty { "No description found!" }
                    ?: "No description found!",
                fontSize = 12.sp,
                color = ColorPrimary.copy(alpha = .74F)
            )
        }

        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .height(110.dp)
                    .fillMaxWidth()
            ) {
                item {
                    MarketItem(title = "Adjusted\nRank", value = exchange.adjustedRank ?: 0)
                }
                item {
                    MarketItem(title = "Reported\nRank", value = exchange.reportedRank ?: 0)
                }
                item {
                    MarketItem(title = "Markets\n", value = exchange.markets ?: 0)
                }
                item {
                    MarketItem(title = "Currencies\n", value = exchange.currencies ?: 0)
                }
            }
        }

        val links = exchange.links
        val validLinks = mapOf<String, List<String>?>(
            "Twitter" to links?.twitter,
            "Website" to links?.website
        ).filter {
            it.value != null && it.let { list ->
                list.value?.isNotEmpty() ?: false
            }
        }.toList()

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (validLinks.isNotEmpty())
                items(validLinks) {
                    LinkItem(linksPair = it, onLinkClick = { uri ->
                        uriHandler.openUri(uri[0])
                    })
                }
        }
    }
}

@Composable
private fun MarketItem(title: String, value: Int) {
/*    val animatedValue by animateIntAsState(
        targetValue = value,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )*/

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = ColorPrimary
        )

        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(54.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = ColorPrimary.copy(alpha = .04F)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value.toString(), fontSize = 24.sp, color = ColorPrimary)
        }
    }
}
