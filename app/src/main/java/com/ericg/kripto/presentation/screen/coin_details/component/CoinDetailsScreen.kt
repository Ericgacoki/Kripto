package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.domain.model.Tag
import com.ericg.kripto.domain.model.TeamMember
import com.ericg.kripto.presentation.screen.coin_details.viewmodel.CoinDetailsViewModel
import com.ericg.kripto.presentation.theme.ColorBadgeText
import com.ericg.kripto.presentation.theme.ColorLink
import com.ericg.kripto.presentation.theme.ColorOrange
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.presentation.ui.sharedComposables.RetryButton
import com.ericg.kripto.util.GifImageLoader
import com.google.accompanist.flowlayout.FlowRow
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CoinDetailsScreen(
    id: String,
    name: String,
    symbol: String,
    rank: Int,
    isNew: Boolean,
    type: String,
    coinDetailsViewModel: CoinDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            CoinDetailsTopBar(name = name, symbol = symbol, rank = rank, type = type, isNew = isNew)
        }
    ) { it ->
        val unUsedPd = it

        val coinDetailsState = coinDetailsViewModel.state.collectAsState()

        LaunchedEffect(key1 = Unit) {
            coinDetailsViewModel.getCoinDetails(id)
        }

        if (coinDetailsState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GifImageLoader(
                    modifier = Modifier.size(250.dp),
                    resource = R.drawable.kripto_loading
                )
            }
        } else if (coinDetailsState.value.coinDetails?.id != null) {
            LazyColumn(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                item {
                    coinDetailsState.value.coinDetails?.description?.let { desc ->
                        Text(
                            text = desc.ifEmpty { "No description" },
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(),
                            fontSize = 14.sp,
                            color = ColorPrimary.copy(alpha = .84F),
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                val tags = coinDetailsState.value.coinDetails?.tags ?: emptyList<Tag>()
                if (tags.isNotEmpty())
                    item {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 4.dp),
                            color = ColorPrimary,
                            text = "Tags",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                if (tags.isNotEmpty())
                    item {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp
                        ) {
                            tags.forEach {
                                TagItem(name = it.name, color = randomColor())
                            }
                        }
                    }

                val members = coinDetailsState.value.coinDetails?.team ?: emptyList<TeamMember>()
                if (members.isNotEmpty())
                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp),
                            color = ColorPrimary,
                            text = "Members",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                if (members.isNotEmpty())
                    item {
                        Box(
                            modifier = Modifier.heightIn(max = 200.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            ) {
                                items(members) { member ->
                                    TeamMemberItem(member.name, member.position)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            listOf(
                                                Color.White,
                                                Color.White.copy(alpha = 0.56F),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 175.dp)
                                    .height(25.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            listOf(
                                                Color.Transparent,
                                                Color.White.copy(alpha = 0.56F),
                                                Color.White,
                                            )
                                        )
                                    )
                            )
                        }
                    }

                item {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        color = ColorPrimary,
                        text = "Links",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                val links = coinDetailsState.value.coinDetails?.links
                val validLinks = mapOf<String, List<String>?>(
                    "Website" to links?.website,
                    "YouTube" to links?.youtube,
                    "Facebook" to links?.facebook,
                    "Reddit" to links?.reddit,
                    "Explorer" to links?.explorer
                ).filter {
                    it.value != null && it.let { list ->
                        list.value?.isNotEmpty() ?: false
                    }
                }.toList()

                if (links != null)
                    item {
                        LazyRow(content = {
                            items(validLinks) { pair ->
                                LinkItem(linksPair = pair) {
                                    // TODO ========= Add BottomSheet
                                }
                            }
                        })
                    }
            }

        } else {
            RetryButton(
                error = coinDetailsState.value.error,
                onRetryEvent = {
                    // TODO: Use UI Event here
                }
            )
        }
    }
}

internal fun randomColor(): Color {
    val colors = listOf<Color>(ColorPrimary, ColorLink,ColorOrange, ColorBadgeText)
    return colors.random()
}