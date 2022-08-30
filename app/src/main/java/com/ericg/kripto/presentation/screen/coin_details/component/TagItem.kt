package com.ericg.kripto.presentation.screen.coin_details.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ericg.kripto.presentation.theme.ColorOrange

@Composable
fun TagItem(name: String, color: Color) {
    Box(
        modifier = Modifier.padding(2.dp)
            .border(width = .30.dp, shape = CutCornerShape(topStart = 12.dp), color = color)
            .clip(shape = CutCornerShape(topStart = 12.dp))
            .background(color.copy(alpha = .24F))
    ) {
        Text(
            modifier = Modifier.padding(top = 6.dp, bottom = 4.dp, start = 12.dp, end = 12.dp),
            text = name,
            color = color,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun TagItemPrev() {
    TagItem(name = "Kripto Currency", ColorOrange)
}