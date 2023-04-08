package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.presentation.content.create_action.ActionProperty
import java.util.*

@Composable
fun CreateActionBottomToolbar(
    modifier: Modifier,
    category: CategoryModel,
    categories: List<CategoryModel>,
    action: CreateActionModel,
    expandPropertyBox: Boolean,
    propertyBoxToShow: ActionProperty,
    onSelectedCategoryClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
    onTimeClicked: () -> Unit,
    onCategorySelected: (CategoryModel) -> Unit,
    onDateSelected: (Date) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Open Calendar",
            modifier = Modifier.padding(6.dp).clickable { onCalendarClicked() },
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "Open Time Picker",
            modifier = Modifier.padding(6.dp).clickable { onTimeClicked() },
            tint = Color.LightGray
        )
        action.date?.let{Text(text = it.toLocaleString())}
        Spacer(modifier = Modifier.weight(1f))
        SelectedCategory(
            category,
            Modifier
                .align(Alignment.CenterVertically)
                .clickable { onSelectedCategoryClicked() },
            propertyBoxToShow == ActionProperty.CATEGORY && expandPropertyBox
        )

    }
    if (expandPropertyBox) {
        ActionPropertiesSelectionBox(
            modifier = Modifier.wrapContentHeight(),
            categories = categories,
            actionProperty = propertyBoxToShow,
            onCategorySelected = onCategorySelected,
            onDateSelected = onDateSelected
        )
    }
}