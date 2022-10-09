package com.ericg.kripto.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ericg.kripto.presentation.screen.NavGraphs
import com.ericg.kripto.presentation.screen.destinations.CoinLIstScreenDestination
import com.ericg.kripto.presentation.screen.destinations.ExchangesScreenDestination
import com.ericg.kripto.presentation.screen.destinations.PriceConversionScreenDestination
import com.ericg.kripto.presentation.theme.KriptoTheme
import com.ericg.kripto.presentation.ui.sharedComposables.BottomNavItem
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class,
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KriptoTheme {
                var showBottomBar by remember { mutableStateOf(true) }
                val navController = rememberAnimatedNavController()
                val navHostEngine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.TopCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = {
                            scaleIn(transformOrigin = TransformOrigin(0.25f, 0f))
                        },
                        exitTransition = {
                            scaleOut(transformOrigin = TransformOrigin(0.75f, 1f))
                        }
                    )
                )

                val systemUiController = rememberSystemUiController()
                val statusBarColor = MaterialTheme.colorScheme.surface
                val useDarkIcons = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = statusBarColor,
                        darkIcons = !useDarkIcons
                    )
                }

                val newBackStackEntry by navController.currentBackStackEntryAsState()
                val route = newBackStackEntry?.destination?.route

                val bottomBarItems: List<BottomNavItem> = listOf(
                    BottomNavItem.CoinList,
                    BottomNavItem.Exchanges,
                    BottomNavItem.PriceConversion
                )

                showBottomBar = route in listOf(
                    CoinLIstScreenDestination.route,
                    ExchangesScreenDestination.route,
                    PriceConversionScreenDestination.route
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigation(
                                modifier = Modifier.height(72.dp),
                                elevation = 8.dp,
                                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                val navBackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackEntry?.destination

                                bottomBarItems.forEach { item ->
                                    val selected =
                                        currentDestination?.route?.contains(item.destination.route) == true
                                    BottomNavigationItem(
                                        icon = {
                                            Box(
                                                modifier = Modifier.fillMaxSize(.62F),
                                                contentAlignment = Center
                                            ) {
                                                if (selected) {
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(vertical = 4.dp)
                                                            .clip(RoundedCornerShape(100))
                                                            .background(MaterialTheme.colorScheme.secondaryContainer)
                                                            .padding(
                                                                horizontal = 20.dp,
                                                                vertical = 6.dp
                                                            ),
                                                        contentAlignment = Center
                                                    ) {
                                                        Icon(
                                                            modifier = Modifier,
                                                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                                            painter = painterResource(id = item.icon),
                                                            contentDescription = "Nav icon"
                                                        )
                                                    }
                                                } else {
                                                    Icon(
                                                        painter = painterResource(id = item.icon),
                                                        contentDescription = "Icon"
                                                    )
                                                }
                                            }
                                        },
                                        label = {
                                            Text(
                                                text = item.title,
                                                modifier = Modifier.padding(top = 2.dp),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        },
                                        alwaysShowLabel = false,
                                        selectedContentColor = MaterialTheme.colorScheme.primary,
                                        unselectedContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
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
                ) {
                    val stupidPadding = it // ignore this padding value

                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        engine = navHostEngine
                    )
                }
            }
        }
    }
}
