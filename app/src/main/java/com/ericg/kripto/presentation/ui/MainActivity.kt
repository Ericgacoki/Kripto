package com.ericg.kripto.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.NavGraphs
import com.ericg.kripto.presentation.screen.destinations.*
import com.ericg.kripto.presentation.theme.ColorPrimary
import com.ericg.kripto.presentation.theme.KriptoTheme
import com.ericg.kripto.presentation.theme.NoRippleTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KriptoTheme {
                var showTopAndBottomBar by remember { mutableStateOf(true) }
                val navController = rememberAnimatedNavController()
                val navHostEngine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.TopCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                )

                val newBackStackEntry by navController.currentBackStackEntryAsState()
                val route = newBackStackEntry?.destination?.route
                val scaffoldState = rememberScaffoldState()

                val bottomBarItems: List<BottomNavItem> = listOf(
                    BottomNavItem.CoinList,
                    BottomNavItem.Exchanges,
                    BottomNavItem.PriceConversion
                )

                showTopAndBottomBar = route in listOf(
                    CoinLIstScreenDestination.route,
                    ExchangesScreenDestination.route,
                    PriceConversionScreenDestination.route
                )

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        if (showTopAndBottomBar) Row(
                            modifier = Modifier
                                .background(ColorPrimary)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = provideTopBarTitle(route) ?: "",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 28.sp,
                                color = Color.White
                            )

                            // Don't show this icon for now
                            if (false) Icon(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        enabled = true,
                                        indication = null
                                    ) {

                                    },
                                painter = painterResource(id = R.drawable.ic_round_sort),
                                tint = Color.White,
                                contentDescription = "Sort"
                            )
                        }

                    },
                    bottomBar = {
                        if (showTopAndBottomBar) {
                            BottomNavigation(
                                backgroundColor = White,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .shadow(
                                        ambientColor = ColorPrimary,
                                        spotColor = ColorPrimary,
                                        shape = RoundedCornerShape(8.dp),
                                        clip = true,
                                        elevation = 16.dp
                                    )
                            ) {
                                val navBackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackEntry?.destination
                                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                                    bottomBarItems.forEach { item ->
                                        val selected =
                                            currentDestination?.route?.contains(item.destination.route) == true
                                        BottomNavigationItem(
                                            icon = {
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {

                                                    if (selected) {
                                                        Box(
                                                            modifier = Modifier
                                                                .padding(all = 4.dp)
                                                                .fillMaxSize()
                                                                .clip(RoundedCornerShape(4.dp))
                                                                .background(ColorPrimary.copy(alpha = .12F))
                                                        ) {
                                                            Row(
                                                                modifier = Modifier
                                                                    .padding(vertical = 8.dp)
                                                                    .fillMaxSize(),
                                                                horizontalArrangement = SpaceAround,
                                                                verticalAlignment = CenterVertically
                                                            ) {
                                                                Icon(
                                                                    modifier = Modifier.padding(
                                                                        start = 4.dp
                                                                    ),
                                                                    painter = painterResource(id = item.icon),
                                                                    tint = if (selected) Color.Unspecified else Color.Unspecified.copy(
                                                                        alpha = .5F
                                                                    ),
                                                                    contentDescription = "Icon"
                                                                )
                                                                Text(
                                                                    modifier = Modifier.padding(
                                                                        top = 2.dp,
                                                                        end = 4.dp
                                                                    ),
                                                                    text = item.title
                                                                )
                                                            }
                                                        }
                                                    } else {
                                                        Icon(
                                                            painter = painterResource(id = item.icon),
                                                            contentDescription = "Icon"
                                                        )
                                                    }
                                                }
                                            },
                                            selected = currentDestination?.route?.contains(item.destination.route) == true,
                                            onClick = {
                                                navController.navigate(item.destination.route) {
                                                    navController.graph.startDestinationRoute?.let { route ->
                                                        popUpTo(route) {
                                                            saveState = true
                                                        }
                                                    }

                                                    restoreState = true
                                                    launchSingleTop = true
                                                }
                                            })
                                    }
                                }
                            }
                        }
                    }
                ) {
                    val stupidPadding = it

                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        engine = navHostEngine
                    )
                }
            }
        }
    }

    private fun provideTopBarTitle(dest: String? = null): String? {
        return when (dest) {
            CoinLIstScreenDestination.route -> "Coins"
            CoinDetailsScreenDestination.route -> "Details"
            ExchangeDetailsScreenDestination.route -> "Market"
            ExchangesScreenDestination.route -> "Markets" // rather than "Exchanges"
            PriceConversionScreenDestination.route -> "Rates"
            SplashScreenDestination.route -> null
            else -> null
        }
    }
}
