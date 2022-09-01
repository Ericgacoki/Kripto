package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.domain.model.Tag
import com.ericg.kripto.domain.model.TeamMember
import com.ericg.kripto.presentation.screen.coin_details.state.BottomSheetContentState
import com.ericg.kripto.presentation.screen.coin_details.viewmodel.CoinDetailsViewModel
import com.ericg.kripto.presentation.theme.*
import com.ericg.kripto.presentation.ui.sharedComposables.RetryButton
import com.ericg.kripto.util.GifImageLoader
import com.google.accompanist.flowlayout.FlowRow
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun CoinDetailsScreen(
    id: String,
    name: String,
    symbol: String,
    rank: Int,
    isNew: Boolean,
    type: String,
    navigator: DestinationsNavigator?,
    coinDetailsViewModel: CoinDetailsViewModel = hiltViewModel()
) {
    var bottomSheetContentState by remember { mutableStateOf(BottomSheetContentState()) }
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = true) {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded)
                bottomSheetScaffoldState.bottomSheetState.collapse()
            else navigator?.navigateUp()
        }
    }
    val uriHandler = LocalUriHandler.current

    BottomSheetScaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            CoinDetailsTopBar(name = name, symbol = symbol, rank = rank, type = type, isNew = isNew)
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = Color.Unspecified.copy(alpha = 0F),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetPeekHeight = 0.dp,
        sheetContent = {
            BottomSheetContent(bottomSheetContentState) {
                uriHandler.openUri(it)
            }
        }
    ) { it ->
        val unUsedPd = it

        val coinDetailsState = coinDetailsViewModel.state.collectAsState()

        LaunchedEffect(key1 = Unit) {
            coinDetailsViewModel.onGetCoinDetailsEvent(id)
        }

        if (coinDetailsState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GifImageLoader(
                    modifier = Modifier.size(250.dp),
                    resource = R.raw.kripto_loading
                )
            }
        } else if (coinDetailsState.value.coinDetails?.id.isNullOrEmpty().not()) {
            LazyColumn(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                item {
                    coinDetailsState.value.coinDetails?.description.let { desc ->
                        Text(
                            text = if (desc.isNullOrEmpty()) "No description" else desc,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(),
                            fontSize = 14.sp,
                            color = ColorPrimary.copy(alpha = .74F),
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
                        val colors =
                            listOf<Color>(ColorPrimary, ColorLinkDark, ColorOrangeDark, ColorBadgeText)

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp
                        ) {
                            tags.sortedBy { it.name.length }.forEach {
                                TagItem(name = it.name, colors = colors)
                            }
                        }
                    }

                val members = coinDetailsState.value.coinDetails?.team ?: emptyList<TeamMember>()
                if (members.isNotEmpty())
                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = 12.dp),
                            color = ColorPrimary,
                            text = "Members",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                if (members.isNotEmpty())
                    item {
                        Box {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 232.dp)
                            ) {
                                items(members) { member ->
                                    TeamMemberItem(member.name, member.position)
                                }
                            }

                            if (members.size > 3)
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(32.dp)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                listOf(Color.White, Color.Transparent)
                                            )
                                        )
                                )

                            if (members.size > 3)
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 200.dp)
                                        .height(32.dp)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.White)
                                            )
                                        )
                                )
                        }
                    }

                val links = coinDetailsState.value.coinDetails?.links
                val validLinks = mapOf<String, List<String>?>(
                    "Explorer" to links?.explorer,
                    "Website" to links?.website,
                    "YouTube" to links?.youtube,
                    "Facebook" to links?.facebook,
                    "Reddit" to links?.reddit
                ).filter {
                    it.value != null && it.let { list ->
                        list.value?.isNotEmpty() ?: false
                    }
                }.toList()

                if (validLinks.isNotEmpty())
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

                if (validLinks.isNotEmpty())
                    item {
                        LazyRow(content = {
                            items(validLinks) { pair ->
                                LinkItem(linksPair = pair) {
                                    coroutineScope.launch {
                                        bottomSheetContentState =
                                            bottomSheetContentState.copy(links = it)
                                        bottomSheetScaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        })
                    }
            }
        } else {
            RetryButton(
                error = coinDetailsState.value.error,
                onRetryEvent = {
                    coinDetailsViewModel.onGetCoinDetailsEvent(id)
                }
            )
        }
    }
}
