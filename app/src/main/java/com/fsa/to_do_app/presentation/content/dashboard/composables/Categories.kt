package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.composables.shapes.DragHandle
import com.fsa.to_do_app.presentation.common.hexToColor

@Composable
fun Categories(
    categories: List<CategoryModel>,
    modifier: Modifier,
    onCategorySelected: (CategoryModel) -> Unit
) {
    Column(modifier) {
        LazyColumn {
            items(categories) {
                Row(Modifier.fillMaxWidth()) {
                    CategoryCard(
                        category = it,
                        modifier = Modifier
                    ) {
                        onCategorySelected(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: CategoryModel,
    modifier: Modifier,
    onCategoryClicked: (CategoryModel) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onCategoryClicked(category) }
            .padding(vertical = 6.dp)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryBottomSheet(
    actionsByCategory: List<ActionModel>,
    category: CategoryModel,
    modalSheetState: ModalBottomSheetState,
    onActionChecked: (id: Int, checked: Boolean) -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetBackgroundColor = category.colorCode.hexToColor(),
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                DragHandle(
                    color = "252A31".hexToColor(alpha = 0.2f),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .align(CenterHorizontally)
                )
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp)) {
                        Text(text = category.name, style = MaterialTheme.typography.h1)
                        Text(
                            text = "${category.numberOfActions} task(s)",
                            modifier = Modifier.padding(top = 2.dp),
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Actions(
                        actions = actionsByCategory,
                        modifier = Modifier,
                        onActionChecked = onActionChecked,
                        showCateg = false
                    )
                }

            }
        }
    ) {

    }

}