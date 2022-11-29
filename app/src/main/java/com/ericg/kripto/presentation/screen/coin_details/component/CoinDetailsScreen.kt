package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ericg.kripto.presentation.ui.sharedComposables.LinkItem
import com.ericg.kripto.presentation.ui.sharedComposables.RetryButton
import com.ericg.kripto.util.GifImageLoader
import com.google.accompanist.flowlayout.FlowRow
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
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
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val uriHandler = LocalUriHandler.current
    BackHandler(enabled = true) {
        coroutineScope.launch {
            if (modalBottomSheetState.isVisible)
                modalBottomSheetState.hide()
            else navigator?.navigateUp()
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .background(colorScheme.surface)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = Color.Unspecified.copy(alpha = 0F),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            BottomSheetContent(bottomSheetContentState) {
                uriHandler.openUri(it)
            }
        }
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CoinDetailsTopBar(
                        name = name,
                        symbol = symbol,
                        rank = rank,
                        type = type,
                        isNew = isNew
                    )
                }
            ) { paddingValues ->

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
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 12.dp
                            )
                            .padding(paddingValues)
                    ) {
                        item {
                            coinDetailsState.value.coinDetails?.description.let { desc ->
                                Text(
                                    text = if (desc.isNullOrEmpty()) "No description" else desc,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 6.dp),
                                    fontSize = 14.sp,
                                    color = colorScheme.onSurface.copy(alpha = .74F),
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }

                        val tags = coinDetailsState.value.coinDetails?.tags ?: emptyList<Tag>()
                        if (tags.isNotEmpty())
                            stickyHeader {
                                Text(
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)
                                        .fillMaxWidth()
                                        .background(colorScheme.surface),
                                    text = "Tags",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        if (tags.isNotEmpty())
                            item {
                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    mainAxisSpacing = 8.dp,
                                    crossAxisSpacing = 8.dp
                                ) {
                                    tags.sortedBy { it.name.length }.forEach {
                                        TagItem(name = it.name)
                                    }
                                }
                            }

                        val links = coinDetailsState.value.coinDetails?.links
                        val validLinks = mapOf<String, List<String>?>(
                            "Explorer" to links?.explorer,
                            "Website" to links?.website,
                            "Source code" to links?.sourceCode,
                            "YouTube" to links?.youtube,
                            "Facebook" to links?.facebook,
                            "Reddit" to links?.reddit
                        ).filter {
                            it.value != null && it.let { list ->
                                list.value?.isNotEmpty() ?: false
                            }
                        }.toList()

                        if (validLinks.isNotEmpty())
                            stickyHeader {
                                Text(
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)
                                        .fillMaxWidth()
                                        .background(colorScheme.surface),
                                    text = "Links",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        if (validLinks.isNotEmpty())
                            item {
                                LazyRow(modifier = Modifier.padding(bottom = 4.dp),
                                    content = {
                                        items(validLinks) { pair ->
                                            LinkItem(linksPair = pair) {
                                                if (it.size > 1) {
                                                    coroutineScope.launch {
                                                        bottomSheetContentState =
                                                            bottomSheetContentState.copy(links = it)
                                                        modalBottomSheetState.show()
                                                    }
                                                } else {
                                                    uriHandler.openUri(it[0])
                                                }
                                            }
                                        }
                                    })
                            }

                        val members =
                            coinDetailsState.value.coinDetails?.team ?: emptyList<TeamMember>()
                        if (members.isNotEmpty())
                            stickyHeader {
                                Text(
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)
                                        .fillMaxWidth()
                                        .background(colorScheme.surface),
                                    text = "Team",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        if (members.isNotEmpty())
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    members.forEach { member ->
                                        TeamMemberItem(member.name, member.position)
                                    }
                                }
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
    }
}
