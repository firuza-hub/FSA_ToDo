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
    val actions by viewModel.actions.collectAsState()
    val actionsByCategory by viewModel.actionsByCategory.collectAsState()
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
        skipHalfExpanded = false,
    )
    if (isLoading) LinearProgressIndicator()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.h1,
                fontFamily = SFPro,
                modifier = Modifier.padding(start = 44.dp, top = 16.dp)
            )

            Actions(actions, Modifier.weight(1f), onActionChecked = viewModel::onActionChecked)

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
            if (modalSheetState.isVisible) {
                CategoryBottomSheet(
                    actionsByCategory,
                    selectedCategory,
                    modalSheetState,
                    viewModel::onActionChecked
                )
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

