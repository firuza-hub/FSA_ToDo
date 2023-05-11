package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.util.getHintOnBackground
import com.fsa.to_do_app.util.getTextOnBackground


@Composable
fun CategoryCard(
    category: CategoryModel,
    modifier: Modifier,
    onCategoryClicked: (CategoryModel) -> Unit,
    onDeleteClicked: (CategoryModel) -> Unit,
    deleteAllowed: Boolean = false
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
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = category.name,
                    color = category.colorCode.hexToColor().getTextOnBackground()
                )
                Text(
                    text = buildString {
                        append(category.numberOfActions)
                        append(stringResource(id = R.string.task_s))
                    },
                    modifier = Modifier.padding(top = 2.dp),
                    style = MaterialTheme.typography.body2,
                    color = category.colorCode.hexToColor().getHintOnBackground()
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            if (deleteAllowed && category.numberOfActions == 0) {
                Icon(
                    modifier = Modifier
                        .noRippleClickable { onDeleteClicked(category) }
                        .padding(end = 16.dp),
                    painter = painterResource(id = R.drawable.ic_trash),
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Delete category"
                )
            }
        }

    }
}
