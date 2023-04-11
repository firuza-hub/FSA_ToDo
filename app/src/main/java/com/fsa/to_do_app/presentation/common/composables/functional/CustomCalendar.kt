package com.fsa.to_do_app.presentation.common.composables.functional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.content.create_action.CalendarState
import com.fsa.to_do_app.presentation.content.dashboard.composables.CategoryIndicator
import java.util.*


@Composable
fun CustomCalendar(
    onDateClicked: (date: Date) -> Unit,
    calendar: CalendarState,
    onMonthUp: () -> Unit,
    onMonthDown: () -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable { onMonthDown() },
                painter = painterResource(id = R.drawable.ic_prev),
                tint = Color.Black.copy(alpha = 0.15f),
                contentDescription = "Previous month"
            )
            Text(
                text = calendar.monthName,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = calendar.year.toString(),
                style = MaterialTheme.typography.body1,
                color = Color.Black.copy(alpha = 0.15f)
            )

            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { onMonthUp() },
                painter = painterResource(id = R.drawable.ic_next),
                tint = Color.Black.copy(alpha = 0.15f),
                contentDescription = "Previous month"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")) {
                Text(
                    text = i,
                    modifier = Modifier
                        .width(50.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(calendar.daysRange) {
                Box {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (it.num == -1) "" else it.num.toString(),
                            modifier = Modifier
                                .clickable {
                                    calendar.selectedDate.set(calendar.year, calendar.month, it.num)
                                    onDateClicked(calendar.selectedDate.time)
                                }
                                .wrapContentWidth()
                                .width(50.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h3,
                            color = if (it.num == calendar.selectedDate.get(Calendar.DAY_OF_MONTH)) Color.Blue else Color.Black
                        )
                        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            it.tasks.take(3).forEach{
                                CategoryIndicator(color = it.categoryColorCode.hexToColor(), modifier = Modifier.padding(horizontal = 1.dp), 5.dp)
                            }
                        }
                    }

                }
            }
        }
    }
}
