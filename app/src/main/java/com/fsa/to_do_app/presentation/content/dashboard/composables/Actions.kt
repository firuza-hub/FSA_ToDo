package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.presentation.common.bottomBorder
import com.fsa.to_do_app.presentation.common.composables.functional.RoundCheckbox
import com.fsa.to_do_app.presentation.common.composables.shapes.CircleShape
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.util.getTimeShort
import java.util.*


@Composable
fun Actions(
    actions: List<ActionModel>,
    modifier: Modifier,
    onActionChecked: (id: Int, checked: Boolean) -> Unit,
    showCateg: Boolean = true
) {
    LazyColumn(modifier) {
        items(actions) { action ->
            Row(Modifier.fillMaxWidth()) {
                RoundCheckbox(
                    checked = action.isDone,
                    onCheckedChange = { onActionChecked(action.id, it) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(28.dp)
                        .align(Alignment.CenterVertically),
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.LightGray,
                        checkedColor = Color.Blue
                    ),
                    borderWidth = 1.5.dp,
                    backgroundColor = if (!showCateg) action.categoryColorCode.hexToColor() else Color.White
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
                            text = action.content, overflow = TextOverflow.Ellipsis, maxLines = 2,
                            style = MaterialTheme.typography.body1
                        )
                        action.date?.let {
                            ActionTime(
                                date = it,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    if (showCateg) {
                        CategoryIndicator(
                            action.categoryColorCode.hexToColor(),
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
fun ActionTime(date: Date, modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            modifier = Modifier
                .height(16.dp)
                .padding(end = 4.dp),
            tint = Color.Gray,
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "Alarm icon"
        )
        Text(text = date.getTimeShort(), style = MaterialTheme.typography.body2)
    }
}

@Composable
fun CategoryIndicator(color: Color, modifier: Modifier) {
    CircleShape(color, modifier)
}



