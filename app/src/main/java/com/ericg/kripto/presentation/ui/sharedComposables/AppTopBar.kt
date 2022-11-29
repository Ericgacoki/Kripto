package com.ericg.kripto.presentation.ui.sharedComposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showSearchBar: Boolean = false,
    initialValue: String,
    onSearchParamChange: (searchParam: String) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                text = title,
                style = typography.headlineMedium
            )

            AnimatedVisibility(visible = showSearchBar) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .clip(CircleShape)
                        .background(colorScheme.onSurface.copy(alpha = .08F))
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    var searchParam: String by remember { mutableStateOf(initialValue) }
                    val focusRequester = remember { FocusRequester() }
                    val focusManager = LocalFocusManager.current

                    TextField(
                        value = searchParam,
                        onValueChange = { newValue ->
                            searchParam = if (newValue.trim().isNotEmpty()) newValue else ""
                            onSearchParamChange(newValue)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequester = focusRequester),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Search...",
                                color = colorScheme.onSurface.copy(alpha = .32F)
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ), keyboardOptions = KeyboardOptions(
                            autoCorrect = true,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSearchParamChange(searchParam)
                            }
                        ),
                        trailingIcon = {
                            Row {
                                AnimatedVisibility(visible = searchParam.trim().isNotEmpty()) {
                                    IconButton(onClick = {
                                        focusManager.clearFocus()
                                        searchParam = ""
                                        onSearchParamChange(searchParam)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = null
                                        )
                                    }
                                }

                                IconButton(onClick = {
                                    onSearchParamChange(searchParam)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
