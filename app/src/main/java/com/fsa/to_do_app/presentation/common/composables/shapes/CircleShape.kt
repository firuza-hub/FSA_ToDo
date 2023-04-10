package com.fsa.to_do_app.presentation.common.composables.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircleShape(color: Color, modifier: Modifier, circleSize: Dp ) {
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(color)
        )
    }
}
