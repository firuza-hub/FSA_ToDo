package com.fsa.to_do_app.presentation.content.create_task.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.create_task.ActionProperty
import com.fsa.to_do_app.presentation.content.create_task.CalendarState
import com.fsa.to_do_app.util.DateFormatter
import java.util.*

@Composable
fun TaskBottomToolbar(
    modifier: Modifier,
    category: CategoryModel,
    categories: List<CategoryModel>,
    timeSet: Boolean,
    taskDate: Date?,
    expandPropertyBox: Boolean,
    propertyBoxToShow: ActionProperty,
    onSelectedCategoryClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    onTimeClicked: () -> Unit,
    onCategorySelected: (CategoryModel) -> Unit,
    onDateSelected: (Date) -> Unit,
    calendar: CalendarState,
    onMonthDown: () -> Unit,
    onMonthUp: () -> Unit,
    onTimePicked: (h: Int, m: Int, ap: String) -> Unit,
    onTimeResetCLicked: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth().imePadding()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Open Calendar",
            modifier = Modifier
                .padding(6.dp)
                .noRippleClickable { onCalendarClicked() },
            tint = if (propertyBoxToShow == ActionProperty.DATE && expandPropertyBox) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "Open Time Picker",
            modifier = Modifier
                .padding(6.dp)
                .noRippleClickable { onTimeClicked() },
            tint = if (propertyBoxToShow == ActionProperty.TIME && expandPropertyBox) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
        )

        taskDate?.let {
            Text(
                text = if (timeSet) DateFormatter.parseDateTime(it) else DateFormatter.parseDate(it),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SelectedCategory(
            category,
            Modifier
                .align(Alignment.CenterVertically)
                .noRippleClickable { onSelectedCategoryClicked() },
            propertyBoxToShow == ActionProperty.CATEGORY && expandPropertyBox
        )

    }
    if (expandPropertyBox) {
        ActionPropertiesSelectionBox(
            modifier = Modifier.wrapContentHeight(),
            categories = categories,
            actionProperty = propertyBoxToShow,
            onCategorySelected = onCategorySelected,
            onDateSelected = onDateSelected,
            calendar = calendar,
            onMonthDown = onMonthDown,
            onMonthUp = onMonthUp,
            onTimePicked = onTimePicked,
            onTimeResetCLicked = onTimeResetCLicked
        )
    }
}