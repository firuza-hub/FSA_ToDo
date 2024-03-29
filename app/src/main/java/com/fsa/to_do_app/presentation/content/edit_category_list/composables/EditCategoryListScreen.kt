package com.fsa.to_do_app.presentation.content.edit_category_list.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.create_task.composables.TaskToolBar
import com.fsa.to_do_app.presentation.content.dashboard.composables.category.Categories
import com.fsa.to_do_app.presentation.content.edit_category_list.EditCategoryListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditCategoryListScreen(
    navigateBack: () -> Unit,
    viewModel: EditCategoryListViewModel = koinViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val newCategoryName by viewModel.newCategoryName.collectAsState()
    val newCategoryColor by viewModel.newCategoryColor.collectAsState()

    Column(Modifier.fillMaxSize()) {
        TaskToolBar(cancel = { }, save = navigateBack, onAreaClicked = {}, showCancel = false)
        Categories(
            categories = categories,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onCategorySelected = {showDialog = true ; viewModel.selectCategoryForEdit(it)},
            onDeleteClicked = viewModel::deleteCategory,
            deleteAllowed = true
        )


        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colors.primaryVariant.copy(0.5f), RoundedCornerShape(10.dp))
                .noRippleClickable { showDialog = true }
                .fillMaxWidth()
                .border(BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(0.1f)), RoundedCornerShape(10.dp))
                .height(70.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Create Category",
                tint = MaterialTheme.colors.onBackground
            )
        }

        CategoryCreationDialog(
            showDialog,
            onDismiss = { showDialog = false; viewModel.resetInputValues() },
            newCategoryName = newCategoryName,
            updateCategoryName = viewModel::updateCategoryName,
            createNewCategory = { viewModel.createNewCategory(); showDialog = false },
            updateCategoryColor = viewModel::updateCategoryColor,
            newCategoryColor = newCategoryColor,
            colors = viewModel.colors
        )
    }
}

