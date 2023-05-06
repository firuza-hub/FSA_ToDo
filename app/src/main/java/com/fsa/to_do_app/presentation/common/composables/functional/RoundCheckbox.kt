package com.fsa.to_do_app.presentation.common.composables.functional

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.hexToColor

@Preview()
@Composable
fun RoundCheckbox(
    checked: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
        .size(28.dp),
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = "006CFF".hexToColor(),
        uncheckedColor = Color.LightGray,
        checkmarkColor = Color.White
    ),
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = MaterialTheme.colors.background
) {
    val fillColor = if (checked) colors.boxColor(enabled = true, state = ToggleableState.On).value
    else backgroundColor

    val borderColor =
        if (checked) colors.borderColor(enabled = true, state = ToggleableState.On).value
        else Color.LightGray
    Box(modifier = modifier) {
        Box(
            modifier = if (enabled)
                Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(borderColor)
                    .padding(borderWidth)
                    .clip(CircleShape)
                    .background(fillColor)
                    .clickable { onCheckedChange(!checked) }
            else Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .clip(CircleShape)
                .background(borderColor)
                .padding(borderWidth)
                .clip(CircleShape)
                .background(fillColor)
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tick),
                    contentDescription = "Task checkbox",
                    tint = colors.checkmarkColor(
                        state = ToggleableState.On
                    ).value,
                    modifier = Modifier.padding(1.dp)
                )
            }
        }

    }


}