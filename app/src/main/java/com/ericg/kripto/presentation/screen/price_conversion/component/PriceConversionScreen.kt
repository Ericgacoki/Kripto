package com.ericg.kripto.presentation.screen.price_conversion.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ericg.kripto.R
import com.ericg.kripto.presentation.screen.price_conversion.event.ConversionUiEvent
import com.ericg.kripto.presentation.screen.price_conversion.state.ConversionState
import com.ericg.kripto.presentation.screen.price_conversion.viewmodel.ConversionViewModel
import com.ericg.kripto.presentation.ui.sharedComposables.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PriceConversionScreen(
    conversionViewModel: ConversionViewModel = hiltViewModel()
) {
    val conversionState = conversionViewModel.conversionState.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "Conversion",
                showSearchBar = false,
                initialValue = "",
                onSearchParamChange = {})
        }
    ) { paddingValues ->
        val baseCurrencies by remember {
            mutableStateOf(
                listOf(
                    // TEST ITEMS
                    Currency(name = "Bitcoin", id = "btc-bitcoin", symbol = "BTC"),
                    Currency(name = "Ethereum", id = "eth-ethereum", symbol = "ETH"),
                    Currency(name = "Tether", id = "usdt-tether", symbol = "USDT"),
                    Currency(name = "Binance Coin", id = "bnb-binance-coin", symbol = "BNB"),
                    Currency(name = "Polygon", id = "matic-polygon", symbol = "MATIC"),
                    Currency(name = "Cardano", id = "ada-cardano", symbol = "ADA"),
                    Currency(name = "Solana", id = "sol-solana", symbol = "SOL"),
                )
            )
        }

        val quoteCurrencies by remember {
            mutableStateOf(
                listOf(
                    // TEST ITEMS
                    Currency(name = "Bitcoin", id = "btc-bitcoin", symbol = "BTC"),
                    Currency(name = "Ethereum", id = "eth-ethereum", symbol = "ETH"),
                    Currency(name = "Tether", id = "usdt-tether", symbol = "USDT"),
                    Currency(name = "Binance Coin", id = "bnb-binance-coin", symbol = "BNB"),
                    Currency(name = "Polygon", id = "matic-polygon", symbol = "MATIC"),
                    Currency(name = "Cardano", id = "ada-cardano", symbol = "ADA"),
                    Currency(name = "Solana", id = "sol-solana", symbol = "SOL"),
                )
            )
        }

        var currencies = Pair(baseCurrencies, quoteCurrencies)

        /**
         * NOTE: The first value of this pair is ALWAYS taken as the
         *       Base Currency regardless of the selection order!
         * */
        var selectedConversionPair by remember {
            mutableStateOf(
                Pair(
                    currencies.first[0],
                    currencies.second[0]
                )
            )
        }
        var isBaseCurrencyMenuExpanded by remember {
            mutableStateOf(false)
        }
        var isQuoteCurrencyMenuExpanded by remember {
            mutableStateOf(false)
        }

        fun swapCurrenciesAndSelectedPair() {
            val swappedPair = Pair(selectedConversionPair.second, selectedConversionPair.first)
            selectedConversionPair = swappedPair

            val swappedCurrencies = Pair(currencies.second, currencies.first)
            currencies = swappedCurrencies
        }

        fun String.takeFirst(n: Int): String {
            return when (this.length) {
                in 0..n -> this
                else -> this.substring(0..n) + "..."
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 12.dp, end = 12.dp, top = 54.dp)
        ) {
            var amountInput by remember { mutableStateOf("") }
            val focusRequester = remember { FocusRequester() }.also {
                LaunchedEffect(key1 = Unit) {
                    it.requestFocus()
                }
            }
            val focusManager = LocalFocusManager.current
            var isError by remember {
                mutableStateOf(false)
            }
            var showConversionState by remember {
                mutableStateOf(false)
            }
            var buttonText by remember {
                mutableStateOf("CONVERT")
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                textStyle = TextStyle.Default.copy(
                    fontSize = 28.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Medium
                ),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                value = amountInput,
                onValueChange = { newValue ->
                    amountInput =
                        newValue.filter { it.isDigit() && it.toString() != "." }.take(12)
                    isError = newValue.trim().isEmpty()
                },
                trailingIcon = {
                    if (isError)
                        Icon(Icons.Filled.Info, null)
                },
                isError = isError,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus(force = true)
                }),
                label = {
                    Text(text = if (isError) "Please enter amount" else "Amount")
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth(),
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = selectedConversionPair.first.name.takeFirst(12),
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            ).also {
                                DropdownMenu(
                                    expanded = isBaseCurrencyMenuExpanded,
                                    onDismissRequest = {
                                        isBaseCurrencyMenuExpanded =
                                            isBaseCurrencyMenuExpanded.not()
                                    }) {

                                    currencies.first.forEachIndexed { index, currency ->
                                        var isChecked: Boolean
                                        DropdownMenuItem(
                                            interactionSource = MutableInteractionSource(),
                                            onClick = {
                                                selectedConversionPair =
                                                    selectedConversionPair.copy(first = currencies.first[index])
                                                isBaseCurrencyMenuExpanded = false
                                                showConversionState = false
                                            }, text = {
                                                Text(
                                                    maxLines = 1,
                                                    text = "(${currency.symbol})  ${currency.name}"
                                                        .takeFirst(22),
                                                    color = colorScheme.inverseSurface.copy(alpha = .84F)
                                                )
                                            },
                                            trailingIcon = {
                                                isChecked =
                                                    currency.id == selectedConversionPair.first.id

                                                if (isChecked)
                                                    Icon(
                                                        modifier = Modifier.padding(start = 12.dp),
                                                        imageVector = Icons.Rounded.CheckCircle,
                                                        contentDescription = "Checked"
                                                    )
                                            }
                                        )
                                    }
                                }
                            }

                            IconButton(modifier = Modifier.size(34.dp),
                                onClick = {
                                    isBaseCurrencyMenuExpanded = isBaseCurrencyMenuExpanded.not()
                                }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Drop down arrow"
                                )
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = selectedConversionPair.second.name.takeFirst(12),
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            ).also {
                                DropdownMenu(
                                    expanded = isQuoteCurrencyMenuExpanded,
                                    onDismissRequest = {
                                        isQuoteCurrencyMenuExpanded =
                                            isQuoteCurrencyMenuExpanded.not()
                                    }) {
                                    var isChecked: Boolean

                                    currencies.second.forEachIndexed { index, currency ->
                                        DropdownMenuItem(
                                            onClick = {
                                                selectedConversionPair =
                                                    selectedConversionPair.copy(second = currencies.second[index])
                                                isQuoteCurrencyMenuExpanded = false
                                                showConversionState = false
                                            }, text = {
                                                Text(
                                                    maxLines = 1,
                                                    text = "(${currency.symbol})  ${currency.name}"
                                                        .takeFirst(22),
                                                    color = colorScheme.inverseSurface.copy(alpha = .84F)
                                                )
                                            }, trailingIcon = {
                                                isChecked =
                                                    currency.id == selectedConversionPair.second.id
                                                if (isChecked)
                                                    Icon(
                                                        modifier = Modifier.padding(start = 12.dp),
                                                        imageVector = Icons.Rounded.CheckCircle,
                                                        contentDescription = "Checked"
                                                    )
                                            }
                                        )
                                    }
                                }
                            }

                            IconButton(modifier = Modifier.size(34.dp),
                                onClick = {
                                    isQuoteCurrencyMenuExpanded = isQuoteCurrencyMenuExpanded.not()
                                }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Drop down arrow"
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                contentAlignment = Center
            ) {
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.tertiary.copy(alpha = .24F)
                    )
                ) {
                    IconButton(onClick = {
                        swapCurrenciesAndSelectedPair()
                        if (!isError && amountInput.isNotEmpty()) {
                            showConversionState = true

                            conversionViewModel.onEvent(
                                ConversionUiEvent.OnConvert(
                                    amount = amountInput.toDouble(),
                                    baseCurrencyId = selectedConversionPair.first.id,
                                    quoteCurrencyId = selectedConversionPair.second.id
                                )
                            )
                        } else showConversionState = false
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_swap),
                            contentDescription = null
                        )
                    }
                }
            }

            val animatedBackgroundColor by animateColorAsState(
                targetValue = if (!isError && amountInput.isNotEmpty()) colorScheme.primary else colorScheme.primary.copy(
                    alpha = .12F
                ),
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 40,
                    easing = LinearOutSlowInEasing
                )
            )

            Button(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .clip(RoundedCornerShape(100))
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = animatedBackgroundColor),
                onClick = {
                    focusManager.clearFocus()
                    if (!isError && amountInput.isNotEmpty()) {
                        showConversionState = true

                        conversionViewModel.onEvent(
                            ConversionUiEvent.OnConvert(
                                amount = amountInput.toDouble(),
                                baseCurrencyId = selectedConversionPair.first.id,
                                quoteCurrencyId = selectedConversionPair.second.id
                            )
                        )
                    } else showConversionState = false
                }
            ) {
                Text(
                    text = buttonText,
                    color = if (!isError && amountInput.isNotEmpty()) colorScheme.onPrimary else
                        colorScheme.onSurface.copy(alpha = .84F),
                    fontWeight = FontWeight.Medium
                )
            }

            LaunchedEffect(key1 = amountInput) {
                showConversionState = false
            }

            AnimatedVisibility(
                visible = showConversionState,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000))
            ) {
                when (conversionState.value) {
                    is ConversionState.NotLoading -> {
                        buttonText = "CONVERT"
                    }
                    is ConversionState.Loading -> {
                        buttonText = "CONVERTING..."
                    }
                    is ConversionState.Error -> {
                        buttonText = "CONVERT"

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp),
                            text = (conversionState.value as ConversionState.Error).message,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    is ConversionState.Success -> {
                        buttonText = "CONVERT"

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            contentAlignment = Center
                        ) {
                            Row(verticalAlignment = CenterVertically) {
                                Text(
                                    text = "${selectedConversionPair.second.symbol}   ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = colorScheme.onSurface.copy(alpha = .61F)
                                )
                                SelectionContainer {
                                    val price =
                                        (conversionState.value as ConversionState.Success).data.price
                                    val text = when {
                                        price.toString().length >= 12 -> {
                                            if (price.toString().contains("E", ignoreCase = true)) {
                                                price.toString()
                                            } else "$price".take(12)
                                        }
                                        else -> price.toString().take(12)
                                    }
                                    Text(
                                        text = text,
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
