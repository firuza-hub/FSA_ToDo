package com.fsa.to_do_app.presentation.content.dashboard.composables

import android.os.Build
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
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter
import com.fsa.to_do_app.presentation.content.dashboard.DashboardViewModel
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
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
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
    var createOptionsExpanded by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    val filterCalendar by viewModel.filterDate.collectAsState()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true, animationSpec = tween(durationMillis = 1000)
    )
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
                        modalSheetState.show()
                    }
                }
            )
        }
        CategoryBottomSheet(
            Modifier.fillMaxWidth().align(BottomCenter),
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
            close = { createOptionsExpanded = false },
            modifier = Modifier
                .align(BottomEnd)
                .padding(30.dp)
        )

        val rotation by animateFloatAsState(
            targetValue = if (createOptionsExpanded) 135f else 0f,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = if (createOptionsExpanded) Spring.DampingRatioHighBouncy else 1f
            )
        )


        val colorFAB by animateColorAsState(
            targetValue = if (createOptionsExpanded) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow
            )
        )

        val colorFABIcon by animateColorAsState(
            targetValue = if (createOptionsExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow
            )
        )

        CircleShape(
            color = colorFAB,
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 30.dp, end = 16.dp)
                .noRippleClickable { createOptionsExpanded = !createOptionsExpanded }
                .rotate(rotation),
            circleSize = 64.dp) {
            Icon(painterResource(id = R.drawable.ic_plus), "Create", tint = colorFABIcon)
        }

        if (showCalendar) {
            DateSelectionBox(filterCalendar, onDatePicked = {
                showCalendar = false
                viewModel.showByDate(filterCalendar)
            }) { showCalendar = false }
        }

    }
}

