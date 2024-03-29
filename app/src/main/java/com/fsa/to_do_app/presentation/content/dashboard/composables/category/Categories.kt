package com.fsa.to_do_app.presentation.content.dashboard.composables.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fsa.to_do_app.domain.model.CategoryModel

@Composable
fun Categories(
    categories: List<CategoryModel>,
    modifier: Modifier,
    onCategorySelected: (CategoryModel) -> Unit,
    onDeleteClicked: (CategoryModel) -> Unit = {},
    deleteAllowed: Boolean = false
) {
    Column(modifier) {
        LazyColumn {
            items(categories) {
                Row(Modifier.fillMaxWidth()) {
                    CategoryCard(
                        category = it,
                        modifier = Modifier,
                        deleteAllowed = deleteAllowed,
                        onCategoryClicked =  onCategorySelected,
                        onDeleteClicked = onDeleteClicked
                    )
                }
            }
        }
    }
}