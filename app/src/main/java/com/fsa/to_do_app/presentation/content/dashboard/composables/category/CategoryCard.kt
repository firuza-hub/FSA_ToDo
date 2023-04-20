package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.noRippleClickable


@Composable
fun CategoryCard(
    category: CategoryModel,
    modifier: Modifier,
    onCategoryClicked: (CategoryModel) -> Unit
) {
    Card(
        modifier = modifier
            .noRippleClickable { onCategoryClicked(category) }
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = category.colorCode.hexToColor()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = category.name)
            Text(
                text = "${category.numberOfActions} tasks",
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.body2
            )
        }
    }
}
