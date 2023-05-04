package com.fsa.to_do_app.presentation.common.composables.functional

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fsa.to_do_app.presentation.common.noRippleClickable
import java.text.DecimalFormat
import kotlin.math.roundToInt

@Composable
fun CustomTimePicker(
    hour: Int,
    minute: Int,
    ap: String,
    onTimePicked: (h: Int, m: Int, ap: String) -> Unit,
    onTimeResetCLicked: () -> Unit
) {
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current

    var _hour by remember { mutableStateOf(if (hour == 0) 12 else hour) }
    var _minute by remember { mutableStateOf(minute) }
    var _ap by remember { mutableStateOf(ap) }

    val hours = (listOf(-1, -1) + (1..12) + listOf(-1, -1)).toList()
    val minutes = (listOf(-1, -1) + (1..60) + listOf(-1, -1)).toList()
    val apList = listOf("", "", "AM", "PM", "", "")

    var hourYCoordinates by remember { mutableStateOf(0) }
    var minuteYCoordinates by remember { mutableStateOf(0) }
    var apYCoordinates by remember { mutableStateOf(0) }

    val offsetPosition = -with(density) { 85.dp.toPx() }

    val hoursState = rememberLazyListState(hours.indexOf(_hour), offsetPosition.toInt())
    val minutesState = rememberLazyListState(minutes.indexOf(_minute), offsetPosition.toInt())
    val apState = rememberLazyListState(apList.indexOf(_ap), offsetPosition.toInt())

    val isHourPressed by hoursState.interactionSource.collectIsDraggedAsState()
    if (!isHourPressed && hourYCoordinates != 93) {
        LaunchedEffect(true) {
            hoursState.scrollToItem(hours.indexOf(_hour), offsetPosition.toInt())
            onTimePicked(_hour, _minute, _ap)
        }
    }
    val isMinutePressed by minutesState.interactionSource.collectIsDraggedAsState()
    if (!isMinutePressed && minuteYCoordinates != 93) {
        LaunchedEffect(true) {
            minutesState.scrollToItem(minutes.indexOf(_minute), offsetPosition.toInt())
            onTimePicked(_hour, _minute, _ap)
        }
    }
    val isAPPressed by apState.interactionSource.collectIsDraggedAsState()
    if (!isAPPressed && apYCoordinates != 93) {
        LaunchedEffect(true) {
            apState.scrollToItem(apList.indexOf(_ap), offsetPosition.toInt())
            onTimePicked(_hour, _minute, _ap)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.TopCenter
    ) {


        Row {
            LazyColumn(state = hoursState) {
                items(hours) {
                    Text(
                        text = if (it == -1) "" else it.toString(),
                        style = MaterialTheme.typography.body1,
                        fontSize = 26.sp,
                        modifier = Modifier
                            .width(40.dp)
                            .padding(vertical = 8.dp)
                            .onGloballyPositioned { layoutCoordinates ->
                                if (with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt() in 70..110) {
                                    _hour = it
                                    hourYCoordinates =
                                        with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt()
                                }
                                if (with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt() == 93)
                                    haptic.performHapticFeedback(
                                        HapticFeedbackType(
                                            HapticFeedbackConstants.LONG_PRESS
                                        )
                                    )
                            }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .width(25.dp)
            )

            val df = DecimalFormat("00")
            LazyColumn(state = minutesState) {
                items(minutes) {
                    Text(
                        text = if (it == -1) "" else df.format(it),
                        style = MaterialTheme.typography.body1,
                        fontSize = 26.sp,
                        modifier = Modifier
                            .width(40.dp)
                            .padding(vertical = 8.dp)
                            .onGloballyPositioned { layoutCoordinates ->
                                if (with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt() in 70..110) {
                                    _minute = it
                                    minuteYCoordinates =
                                        with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt()
                                }
                            }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .width(25.dp)
            )

            LazyColumn(state = apState) {
                items(apList) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1,
                        fontSize = 26.sp,
                        modifier = Modifier
                            .width(60.dp)
                            .padding(vertical = 8.dp)
                            .onGloballyPositioned { layoutCoordinates ->
                                if (with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt() in 70..110) {
                                    _ap = it
                                    apYCoordinates =
                                        with(density) { layoutCoordinates.positionInParent().y.toDp() }.value.roundToInt()
                                }
                            }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.Transparent,
                            Color.White
                        )
                    )
                )
        ) {
            Text(
                text = "Reset",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(
                    Alignment.TopEnd
                ).padding(16.dp).noRippleClickable(onTimeResetCLicked)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .align(Alignment.CenterStart)
                .border(1.dp, Color.Black.copy(0.1f))
        )

    }
}
