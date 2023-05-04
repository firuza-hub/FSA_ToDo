package com.fsa.to_do_app.presentation.content.create_task.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.composables.functional.CustomCalendar
import com.fsa.to_do_app.presentation.common.composables.functional.CustomTimePicker
import com.fsa.to_do_app.presentation.content.create_task.ActionProperty
import com.fsa.to_do_app.presentation.content.create_task.CalendarState
import com.fsa.to_do_app.presentation.content.dashboard.composables.Categories
import com.fsa.to_do_app.util.intToAmPm
import java.util.*

@Composable
fun ActionPropertiesSelectionBox(
    modifier: Modifier,
    actionProperty: ActionProperty,
    categories: List<CategoryModel>,
    onCategorySelected: (CategoryModel) -> Unit,
    onDateSelected: (Date) -> Unit,
    onTimePicked: (h: Int, m: Int, ap: String) -> Unit,
    calendar: CalendarState,
    onMonthDown: () -> Unit,
    onMonthUp: () -> Unit,
    onTimeResetCLicked: () -> Unit

) {
    Box(modifier = modifier.padding( bottom = 16.dp)) {
        when (actionProperty) {
            ActionProperty.CATEGORY -> {
                Categories(
                    categories = categories,
                    modifier = Modifier.padding( start = 16.dp, end = 16.dp),
                    onCategorySelected = onCategorySelected
                )
            }
            ActionProperty.DATE -> {
                CustomCalendar(
                    onDateClicked = onDateSelected,
                    calendar,
                    onMonthDown = onMonthDown,
                    onMonthUp = onMonthUp
                )
            }
            ActionProperty.TIME -> {
                CustomTimePicker(
                    calendar.selectedDate.get(Calendar.HOUR),
                    calendar.selectedDate.get(Calendar.MINUTE),
                    calendar.selectedDate.get(Calendar.AM_PM).intToAmPm(),
                    onTimePicked,
                    onTimeResetCLicked
                )
            }
        }
    }
}



