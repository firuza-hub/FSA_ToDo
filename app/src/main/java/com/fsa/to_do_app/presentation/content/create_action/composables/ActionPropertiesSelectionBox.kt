package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.content.create_action.ActionProperty
import com.fsa.to_do_app.presentation.content.dashboard.composables.Categories
import java.util.*
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.content.create_action.CalendarState

@Composable
fun ActionPropertiesSelectionBox(
    modifier: Modifier,
    actionProperty: ActionProperty,
    categories: List<CategoryModel>,
    onCategorySelected: (CategoryModel) -> Unit,
    onDateSelected: (Date) -> Unit,
    calendar: CalendarState,
    onMonthDown:() -> Unit,
    onMonthUp :() -> Unit

) {
    Box(modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        when (actionProperty) {
            ActionProperty.CATEGORY -> {
                Categories(
                    categories = categories,
                    modifier = Modifier,
                    onCategorySelected = onCategorySelected
                )
            }
            ActionProperty.DATE -> {
                CustomCalendar(onDateClicked = onDateSelected, calendar, onMonthDown = onMonthDown ,onMonthUp = onMonthUp)
            }
            ActionProperty.TIME -> {}
        }
    }
}


@Composable
fun CustomCalendar(
    onDateClicked: (date: Date) -> Unit,
    calendar: CalendarState,
    onMonthUp: () -> Unit,
    onMonthDown: () -> Unit
) {

    Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Icon(
                modifier = Modifier.padding(end = 16.dp).clickable { onMonthDown() },
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
                modifier = Modifier.padding(start = 16.dp).clickable { onMonthUp() },
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
                Text(
                    text = if (it == -1) "" else it.toString(),
                    modifier = Modifier
                        .clickable {
                            calendar.selectedDate.set(calendar.year, calendar.month, it)
                            onDateClicked(calendar.selectedDate.time)
                        }
                        .wrapContentWidth()
                        .width(50.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3,
                    color = if (it == calendar.selectedDate.get(Calendar.DAY_OF_MONTH)) Color.Blue else Color.Black
                )
            }
        }
    }
}


