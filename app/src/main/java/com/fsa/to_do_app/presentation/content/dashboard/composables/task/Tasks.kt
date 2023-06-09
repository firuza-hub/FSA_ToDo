package com.fsa.to_do_app.presentation.content.dashboard.composables

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.presentation.common.composables.shapes.CircleShape
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter
import com.fsa.to_do_app.util.getDateShort
import com.fsa.to_do_app.util.getTimeShort
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Tasks(
    tasks: List<TaskModel>,
    modifier: Modifier,
    onTaskChecked: (id: Int, checked: Boolean) -> Unit,
    showCategory: Boolean = true,
    deleteTask: (TaskModel, onSuccess: () -> Unit) -> Unit = { _, _ -> },
    allShown: DashboardFilter,
    onTaskClicked: (Int) -> Unit = {},
    circleColor: Color = Color.LightGray
) {
    val context = LocalContext.current
    val msg = stringResource(R.string.msg_deleted)
    LazyColumn(modifier) {
        items(items = tasks, key = { it.id }) { task ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        deleteTask(task) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                        true
                    } else false
                }
            )
            if (showCategory) {
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colors.background
                                else -> MaterialTheme.colors.error
                            }
                        )
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            if (dismissState.targetValue != DismissValue.Default) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        }
                    },
                    dismissThresholds = { FractionalThreshold(0.2f) },
                    directions = setOf(DismissDirection.EndToStart)
                ) {
                    Task(task, onTaskChecked, showCategory, allShown, onTaskClicked, circleColor)
                }
            } else {
                Task(task, onTaskChecked, showCategory, allShown, circleColor = circleColor)
            }
        }
    }
}


@Composable
fun ActionTime(date: Date, timeSet: Boolean, allShown: DashboardFilter, modifier: Modifier) {
    Row(verticalAlignment = CenterVertically, modifier = modifier) {
        if (timeSet) {
            Icon(
                modifier = Modifier
                    .height(16.dp)
                    .padding(end = 4.dp),
                tint = Color.Gray,
                painter = painterResource(id = R.drawable.ic_alarm),
                contentDescription = "Alarm icon"
            )
            Text(
                text = if (allShown == DashboardFilter.ShowAll) date.getDateShort() else date.getTimeShort(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun CategoryIndicator(color: Color, modifier: Modifier, circleSize: Dp = 12.dp) {
    CircleShape(color, modifier, circleSize)
}



