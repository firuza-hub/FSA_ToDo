package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.hexToColor


@Composable
fun CreateTaskOrCategoryMenu(
    expanded: Boolean,
    close: () -> Unit,
    modifier: Modifier,
    onCreateCategoryClicked: () -> Unit,
    onCreateTaskClicked: () -> Unit

) {
    Box(
        modifier = modifier.width(220.dp)
    ) {
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(10))
        ) {
            DropdownMenu(
                expanded = expanded, onDismissRequest = close,
                modifier = Modifier.width(220.dp)
            ) {
                DropdownMenuItem(onClick = { onCreateTaskClicked(); close() }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.ic_task),
                            "Task",
                            tint = "006CFF".hexToColor()
                        )
                        Text(
                            "Task",
                            style = MaterialTheme.typography.body1,
                            color = "006CFF".hexToColor(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Divider(Modifier.fillMaxWidth(), Color.Black.copy(0.1f))
                DropdownMenuItem(onClick = {onCreateCategoryClicked(); close() }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(id = R.drawable.ic_list),
                            "List",
                            tint = "006CFF".hexToColor()
                        )
                        Text(
                            "List",
                            style = MaterialTheme.typography.body1,
                            color = "006CFF".hexToColor(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

