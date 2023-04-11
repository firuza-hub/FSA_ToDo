package com.fsa.to_do_app.presentation.common.composables.functional

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTimePicker() {
    var hour by remember { mutableStateOf(5) }
    var minute by remember { mutableStateOf(41) }

    val hours = (1..12).toList()
    val minutes = (1..60).toList()


    val minuteOffsetPosition = -200
    val hourOffsetPosition = -200

    val hoursState = rememberLazyListState(hours.indexOf(hour),hourOffsetPosition)
    val minutesState = rememberLazyListState(minutes.indexOf(minute),minuteOffsetPosition)

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
                        text = it.toString(),
                        style = MaterialTheme.typography.body1,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(25.dp))
            LazyColumn(state = minutesState) {
                items(minutes) {
                    Text(
                        text = it.toString(),
                        style = MaterialTheme.typography.body1,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
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
        )
    }
}
