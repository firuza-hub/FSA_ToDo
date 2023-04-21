package com.fsa.to_do_app.presentation.content.edit_category_list.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.presentation.common.noRippleClickable

@Composable
fun ColorPaletteItem(
    color: Color, borderWidth: Dp, onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    Canvas(modifier = Modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(20.dp))
        .border(
            borderWidth,
            MaterialTheme.colors.onBackground.copy(alpha = 0.75f),
            RoundedCornerShape(20.dp)
        )
        .background(color)
        .requiredSize(70.dp)
        .noRippleClickable {
            onColorSelected(color)
            onDismiss()
        }
    ) {
    }
}