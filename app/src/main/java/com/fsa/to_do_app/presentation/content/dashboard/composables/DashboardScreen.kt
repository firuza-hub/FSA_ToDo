package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fsa.to_do_app.presentation.content.dashboard.DashboardViewModel
import com.fsa.to_do_app.presentation.theme.SFPro
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToCreateAction: () -> Unit
) {
    viewModel.loadData()
    val tasks by viewModel.tasks.collectAsState()
    val tasksByCategory by viewModel.tasksByCategory.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val categorySheetState by viewModel.categorySheetState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    val modalSheetState = rememberModalBottomSheetState(
        initialValue = categorySheetState,
        confirmValueChange = {
            viewModel.updateCategorySheetState(it)
            it != ModalBottomSheetValue.HalfExpanded
        },
        skipHalfExpanded = true,
    )
    if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {


            Spacer(modifier = Modifier.height(16.dp))
            if (modalSheetState.isVisible) {
                CategoryBottomSheet(
                    tasksByCategory,
                    selectedCategory,
                    modalSheetState,
                    viewModel::onTaskChecked
                )
            }
            Text(
                text = "Today",
                style = MaterialTheme.typography.h1,
                fontFamily = SFPro,
                modifier = Modifier.padding(start = 44.dp)
            )

            Tasks(tasks, Modifier.weight(1f), onTaskChecked = viewModel::onTaskChecked, deleteTask = viewModel::delete)

            if (categories.any()) {
                Text(
                    text = "Lists",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 6.dp, start = 45.dp)
                )
            }

            Categories(
                categories,
                Modifier
                    .weight(1f)
                    .padding(end = 16.dp, start = 45.dp)
            ) {
                viewModel.updateSelectedCategory(it)
                viewModel.updateCategorySheetState(ModalBottomSheetValue.Expanded)
            }
        }


        FloatingActionButton(
            onClick = navigateToCreateAction,
            backgroundColor = Color.White,
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 30.dp, end = 16.dp)
        ) {
            Text(text = "+", fontSize = 30.sp, color = Color.Blue)
        }
    }

}

