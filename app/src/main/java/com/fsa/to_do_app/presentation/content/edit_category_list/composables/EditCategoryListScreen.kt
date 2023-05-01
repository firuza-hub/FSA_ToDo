package com.fsa.to_do_app.presentation.content.edit_category_list.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.create_task.composables.TaskToolBar
import com.fsa.to_do_app.presentation.content.dashboard.composables.Categories
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
            onCategorySelected = {},
            onDeleteClicked = viewModel::deleteCategory,
            deleteAllowed = true
        )


        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.Gray.copy(0.1f), RoundedCornerShape(10.dp))
                .noRippleClickable { showDialog = true }
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Black.copy(0.1f)), RoundedCornerShape(10.dp))
                .height(70.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Create Category"
            )
        }

        CategoryCreationDialog(
            showDialog, closeDialog = { showDialog = false },
            newCategoryName = newCategoryName,
            updateCategoryName = viewModel::updateCategoryName,
            createNewCategory = { viewModel.createNewCategory() },
            updateCategoryColor = viewModel::updateCategoryColor,
            newCategoryColor = newCategoryColor, colors = viewModel.colors
        )
    }
}

