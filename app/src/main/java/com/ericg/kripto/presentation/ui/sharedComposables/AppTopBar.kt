package com.ericg.kripto.presentation.ui.sharedComposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import com.ericg.kripto.presentation.theme.ColorPrimary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppTopBar(
    title: String,
    showSearchBar: Boolean,
    onSearchParamChange: (searchParam: String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = ColorPrimary
        )

        if (showSearchBar) Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clip(CircleShape)
                .background(Color(0XFFEEEEEE))
                .fillMaxWidth()
                .height(54.dp)
        ) {
            var searchParam: String by remember { mutableStateOf("") }
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current

            TextField(
                value = searchParam,
                onValueChange = { newValue ->
                    searchParam = if (newValue.trim().isNotEmpty()) newValue else ""
                },
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester = focusRequester),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Search...",
                        color = ColorPrimary.copy(alpha = 0.56F)
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    textColor = ColorPrimary,
                    backgroundColor = Color.Transparent,
                    disabledTextColor = Color.LightGray,
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
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    tint = ColorPrimary.copy(alpha = 0.64F),
                                    contentDescription = null
                                )
                            }
                        }

                        IconButton(onClick = {
                            onSearchParamChange(searchParam)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                tint = ColorPrimary.copy(alpha = 0.64F),
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    }
}
