package com.fsa.to_do_app.ui.content.dashboard.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.ui.common.hexToColor

@Composable
fun Categories(categories: List<CategoryModel>, modifier: Modifier) {
    Column(modifier) {
        Text(
            text = "Lists",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        LazyColumn {
            items(categories) {
                Row(Modifier.fillMaxWidth()) {
                    CategoryCard(category = it, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: CategoryModel, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = category.colorCode.hexToColor()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = category.name)
            Text(
                text = "${category.numberOfTasks} tasks",
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.body2
            )
        }
    }

}