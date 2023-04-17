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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.presentation.common.bottomBorder
import com.fsa.to_do_app.presentation.common.composables.functional.RoundCheckbox
import com.fsa.to_do_app.presentation.common.composables.shapes.CircleShape
import com.fsa.to_do_app.presentation.common.hexToColor
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
    allShown: DashboardFilter
) {
    val context = LocalContext.current
    LazyColumn(modifier) {
        items(items = tasks, key = { it.id }) { task ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        deleteTask(task) {
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        }
                        true
                    } else false
                }
            )
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> Color.White
                            else -> Color.Red
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
                RoundCheckbox(
                    checked = task.isDone,
                    onCheckedChange = { onTaskChecked(task.id, it) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(28.dp)
                        .align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.LightGray,
                        checkedColor = Color.Blue
                    ),
                    borderWidth = 1.5.dp,
                    backgroundColor = if (!showCategory) task.categoryColorCode.hexToColor() else Color.White
                )
                Row(modifier = Modifier.bottomBorder(1.dp, Color.Black.copy(alpha = 0.1f))) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = task.content, overflow = TextOverflow.Ellipsis, maxLines = 2,
                            style = MaterialTheme.typography.body1
                        )
                        task.date?.let {
                            ActionTime(
                                date = it, allShown,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    if (showCategory) {
                        CategoryIndicator(
                            task.categoryColorCode.hexToColor(),
                            Modifier
                                .align(Alignment.CenterVertically)
                                .wrapContentWidth(unbounded = true)
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActionTime(date: Date, allShown: DashboardFilter, modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
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

@Composable
fun CategoryIndicator(color: Color, modifier: Modifier, circleSize: Dp = 12.dp) {
    CircleShape(color, modifier, circleSize)
}



