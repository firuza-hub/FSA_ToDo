package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.presentation.common.bottomBorder
import com.fsa.to_do_app.presentation.common.composables.functional.RoundCheckbox
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter
import com.fsa.to_do_app.util.getTextOnBackground

@Composable
fun Task(
    task: TaskModel,
    onTaskChecked: (id: Int, checked: Boolean) -> Unit,
    showCategory: Boolean,
    allShown: DashboardFilter,
    onClick: (Int) -> Unit = {},
    circleColor: Color
) {
    Row {
        RoundCheckbox(
            checked = task.isDone,
            onCheckedChange = { onTaskChecked(task.id, it) },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(28.dp)
                .align(Alignment.CenterVertically),
            colors = CheckboxDefaults.colors(
                uncheckedColor = circleColor,
                checkedColor = Color.Blue
            ),
            borderWidth = 1.5.dp,
            backgroundColor = if (!showCategory) task.category.colorCode.hexToColor() else MaterialTheme.colors.background
        )
        Row(
            modifier = if (showCategory) Modifier
                .noRippleClickable { onClick(task.id) }
                .bottomBorder(1.dp, Color.Black.copy(alpha = 0.1f)) else
                Modifier.bottomBorder(1.dp, Color.Black.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.content,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.body1,
                    color = if (showCategory) MaterialTheme.colors.onBackground else task.category.colorCode.hexToColor()
                        .getTextOnBackground()
                )
                task.date?.let {
                    ActionTime(
                        date = it, task.timeSet, allShown,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            if (showCategory) {
                CategoryIndicator(
                    task.category.colorCode.hexToColor(),
                    Modifier
                        .align(Alignment.CenterVertically)
                        .wrapContentWidth(unbounded = true)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}
