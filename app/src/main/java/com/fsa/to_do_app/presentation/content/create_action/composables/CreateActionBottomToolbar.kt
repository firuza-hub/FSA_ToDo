package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.create_action.ActionProperty
import com.fsa.to_do_app.presentation.content.create_action.CalendarState
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CreateActionBottomToolbar(
    modifier: Modifier,
    category: CategoryModel,
    categories: List<CategoryModel>,
    action: CreateTaskModel,
    expandPropertyBox: Boolean,
    propertyBoxToShow: ActionProperty,
    onSelectedCategoryClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    onTimeClicked: () -> Unit,
    onCategorySelected: (CategoryModel) -> Unit,
    onDateSelected: (Date) -> Unit,
    calendar: CalendarState,
    onMonthDown: () -> Unit,
    onMonthUp: () -> Unit
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
            tint = if (propertyBoxToShow == ActionProperty.DATE && expandPropertyBox) Color.Blue else Color.LightGray
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "Open Time Picker",
            modifier = Modifier
                .padding(6.dp)
                .noRippleClickable { onTimeClicked() },
            tint = if (propertyBoxToShow == ActionProperty.TIME && expandPropertyBox) Color.Blue else Color.LightGray
        )
        val sdf = SimpleDateFormat.getDateInstance(DateFormat.DATE_FIELD)
        action.date?.let {
            Text(
                text = sdf.format(it), style = MaterialTheme.typography.body2,
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
            onMonthUp = onMonthUp
        )
    }
}