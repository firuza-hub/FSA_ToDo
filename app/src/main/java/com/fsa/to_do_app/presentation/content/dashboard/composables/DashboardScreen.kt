package com.fsa.to_do_app.presentation.content.dashboard.composables

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.composables.shapes.CircleShape
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter
import com.fsa.to_do_app.presentation.content.dashboard.DashboardViewModel
import com.fsa.to_do_app.presentation.content.dashboard.composables.category.Categories
import com.fsa.to_do_app.presentation.content.dashboard.composables.menu.DashboardFilterMenu
import com.fsa.to_do_app.presentation.theme.SFPro
import com.fsa.to_do_app.util.getDateShort
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToCreateTask: () -> Unit,
    navigateToEditTask: (Int) -> Unit,
    navigateToCreateCategory: () -> Unit,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    LaunchedEffect(key1 = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.loadData()
    }
    val scope = rememberCoroutineScope()
    val tasks by viewModel.tasks.collectAsState()
    val tasksByCategory by viewModel.tasksByCategory.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val allShown by viewModel.allShown.collectAsState()
    var filterOptionsExpanded by remember { mutableStateOf(false) }
    val createOptionsExpanded by viewModel.createOptionsExpanded.collectAsState()
    var showCalendar by remember { mutableStateOf(false) }
    val filterCalendar by viewModel.filterDate.collectAsState()
    val isModalClosed by viewModel.isModalClosed.collectAsState()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true, animationSpec = tween(durationMillis = 1000)
    )
    LaunchedEffect(true) {
        viewModel.isModalClosed.collect {
            Log.d(
                "MODAL_STATE",
                "$it"
            )
        }
    }
    LaunchedEffect(true) {
        snapshotFlow { modalSheetState.isVisible }.collect { isModalVisible ->
            Log.d("MODAL_STATE", "set modal visible to $isModalVisible")
            if (isModalVisible.not()) {
                viewModel.changeModalState(open = false)
            }
        }
    }

    BackHandler(!isModalClosed) {
        scope.launch {
            modalSheetState.hide()
             viewModel.changeModalState(open = false)
        }
    }

    if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = when (allShown) {
                        DashboardFilter.ShowAll -> stringResource(R.string.header_AllTasks)
                        DashboardFilter.ShowToday -> stringResource(R.string.header_Today)
                        DashboardFilter.ShowByDate -> filterCalendar.time.getDateShort()
                    },
                    style = MaterialTheme.typography.h1.copy(color = MaterialTheme.colors.onBackground),
                    fontFamily = SFPro,
                    modifier = Modifier.padding(start = 44.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "More",
                    modifier = Modifier
                        .noRippleClickable {
                            filterOptionsExpanded = !filterOptionsExpanded
                        }
                        .padding(end = 10.dp)
                        .size(30.dp),
                    tint = if (filterOptionsExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant)


            }
            Tasks(
                tasks,
                Modifier.weight(1f),
                onTaskChecked = viewModel::onTaskChecked,
                deleteTask = viewModel::delete,
                allShown = allShown,
                onTaskClicked = navigateToEditTask
            )

            if (categories.any()) {
                Text(
                    text = stringResource(R.string.header_Lists),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 6.dp, start = 45.dp)
                )
            }

            Categories(
                categories = categories,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp, start = 45.dp),
                {
                    viewModel.updateSelectedCategory(it)
                    scope.launch {
                        viewModel.changeModalState(open = true)
                        modalSheetState.show()
                    }
                }
            )
        }
        CategoryBottomSheet(
            Modifier
                .fillMaxWidth()
                .align(BottomCenter),
            tasksByCategory,
            selectedCategory,
            modalSheetState,
            viewModel::onTaskChecked,
            allShown = allShown
        )


        DashboardFilterMenu(
            allShown = allShown,
            onShowAllClicked = viewModel::showAll,
            onShowTodayClicked = viewModel::showToday,
            onCalendarClicked = { showCalendar = true },
            expanded = filterOptionsExpanded,
            close = { filterOptionsExpanded = false },
            modifier = Modifier
                .align(TopEnd)
                .padding(30.dp)
        )
        CreateTaskOrCategoryMenu(
            onCreateCategoryClicked = navigateToCreateCategory,
            onCreateTaskClicked = navigateToCreateTask,
            expanded = createOptionsExpanded,
            close = { viewModel.changeCreateOptionsExpanded(false) },
            modifier = Modifier
                .align(BottomEnd)
                .padding(30.dp)
        )

        val rotation by animateFloatAsState(
            targetValue = if (createOptionsExpanded) 135f else 0f,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = if (createOptionsExpanded) Spring.DampingRatioHighBouncy else 1f
            ), label = ""
        )


        val colorFAB by animateColorAsState(
            targetValue = if (createOptionsExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow
            ), label = ""
        )

        val colorFABIcon by animateColorAsState(
            targetValue = if (createOptionsExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow
            ), label = ""
        )

        CircleShape(
            color = colorFAB,
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 30.dp, end = 16.dp)
                .noRippleClickable { viewModel.changeCreateOptionsExpanded(!createOptionsExpanded)  }
                .rotate(rotation),
            circleSize = 64.dp) {
            Icon(painterResource(id = R.drawable.ic_plus), "Create", tint = colorFABIcon)
        }

        LaunchedEffect(createOptionsExpanded ){
            Log.d("FAB_ROTATION", "createOptionsExpanded: $createOptionsExpanded")
        }

        if (showCalendar) {
            DateSelectionBox(filterCalendar, onDatePicked = {
                showCalendar = false
                viewModel.showByDate(filterCalendar)
            }) { showCalendar = false }
        }

    }
}



