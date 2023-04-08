package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.content.create_action.ActionProperty
import com.fsa.to_do_app.presentation.content.dashboard.composables.Categories
import com.fsa.to_do_app.util.DateFormatter.month_date
import com.fsa.to_do_app.util.getDayOfWeek
import java.util.*

@Composable
fun ActionPropertiesSelectionBox(
    modifier: Modifier,
    actionProperty: ActionProperty,
    categories: List<CategoryModel>,
    onCategorySelected: (CategoryModel) -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val currentDate = Calendar.getInstance(TimeZone.getDefault())
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
                CustomCalendar(onDateClicked = onDateSelected,currentDate, {}, {})
            }
            ActionProperty.TIME -> {}
        }
    }
}

fun getMonthDays():List<Int>{
    val calendar = Calendar.getInstance(TimeZone.getDefault())

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayOfMonth_WeekDay = calendar.getDayOfWeek()
    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE)

    val currentMonth = mutableListOf<Int>()
    for (i in 1 until firstDayOfMonth_WeekDay) currentMonth.add(-1)
    for (i in 1 until lastDayOfMonth) currentMonth.add(i)

    return currentMonth
}

@Composable
fun CustomCalendar(onDateClicked: (date: Date) -> Unit, currentDate:Calendar,  onMonthUp:() -> Unit, onMonthDown: () -> Unit) {

    val year = currentDate.get(Calendar.YEAR)
    val month = currentDate.get(Calendar.MONTH)
    val monthString = month_date.format(currentDate.time)
    val currentMonthDays = getMonthDays()

    Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = monthString,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = year.toString(),
                style = MaterialTheme.typography.body1,
                color = Color.Black.copy(alpha = 0.15f)
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
            items(currentMonthDays) {
                Text(
                    text = if (it == -1) "" else it.toString(),
                    modifier = Modifier
                        .clickable {
                            currentDate.set(year, month, it)
                            onDateClicked(currentDate.time)
                        }
                        .wrapContentWidth()
                        .width(50.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3,
                    color = if(it == currentDate.get(Calendar.DAY_OF_MONTH)) Color.Blue else Color.Black
                )
            }
        }
    }
}


