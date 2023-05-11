package com.fsa.to_do_app.presentation.content.create_task.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.content.dashboard.composables.CategoryIndicator

@Composable
fun SelectedCategory(
    category: CategoryModel,
    modifier: Modifier,
    isExpanded: Boolean
) {
    Row(modifier = modifier) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.subtitle1,
            color = if (isExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
        )
        CategoryIndicator(
            category.colorCode.hexToColor(),
            Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(unbounded = true)
                .padding(horizontal = 16.dp)
        )
    }
}
