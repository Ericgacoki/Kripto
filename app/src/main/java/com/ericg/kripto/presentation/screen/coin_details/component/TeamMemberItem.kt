package com.ericg.kripto.presentation.screen.coin_details.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ericg.kripto.R

@Composable
fun TeamMemberItem(name: String, position: String) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp, top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_person_icon),
            tint = colorScheme.tertiary.copy(alpha = 0.99F),
            modifier = Modifier
                .size(48.dp)
                .clip(shape = CircleShape),
            contentDescription = "Person Icon"
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .height(48.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                color = colorScheme.onSurface.copy(alpha = 0.56F),
                text = position,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
