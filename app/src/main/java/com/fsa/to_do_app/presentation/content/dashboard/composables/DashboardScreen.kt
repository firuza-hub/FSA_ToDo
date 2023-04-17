package com.fsa.to_do_app.presentation.content.dashboard.composables

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter
import com.fsa.to_do_app.presentation.content.dashboard.DashboardViewModel
import com.fsa.to_do_app.presentation.theme.SFPro
import com.fsa.to_do_app.util.getDateShort
import org.koin.androidx.compose.koinViewModel
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(), navigateToCreateAction: () -> Unit
) {
    viewModel.loadData()
    val tasks by viewModel.tasks.collectAsState()
    val tasksByCategory by viewModel.tasksByCategory.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val categorySheetState by viewModel.categorySheetState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val allShown by viewModel.allShown.collectAsState()
    var filterOptionsExpanded by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    val filterCalendar by viewModel.filterDate.collectAsState()

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
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            if (modalSheetState.isVisible) {
                CategoryBottomSheet(
                    tasksByCategory,
                    selectedCategory,
                    modalSheetState,
                    viewModel::onTaskChecked,
                    allShown = allShown
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = when (allShown) {
                        DashboardFilter.ShowAll -> "All Tasks"
                        DashboardFilter.ShowToday ->  "Today"
                        DashboardFilter.ShowByDate -> filterCalendar.time.getDateShort()
                    },
                    style = MaterialTheme.typography.h1,
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
                    tint = if (filterOptionsExpanded) Color.Blue else Color.Gray)


            }
            Tasks(
                tasks,
                Modifier.weight(1f),
                onTaskChecked = viewModel::onTaskChecked,
                deleteTask = viewModel::delete,
                allShown = allShown
            )

            if (categories.any()) {
                Text(
                    text = "Lists",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 6.dp, start = 45.dp)
                )
            }

            Categories(
                categories = categories,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp, start = 45.dp)
            ) {
                viewModel.updateSelectedCategory(it)
                viewModel.updateCategorySheetState(ModalBottomSheetValue.Expanded)
            }
        }

        DashboardFilterMenu(
            allShown = allShown,
            onShowAllClicked = viewModel::showAll,
            onShowTodayClicked = viewModel::showToday,
            onCalendarClicked = { showCalendar = !showCalendar },
            expanded = filterOptionsExpanded,
            close = { filterOptionsExpanded = false },
            modifier = Modifier
                .align(TopEnd)
                .padding(30.dp)
        )
        FloatingActionButton(
            onClick = navigateToCreateAction,
            backgroundColor = Color.White,
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 30.dp, end = 16.dp)
        ) {
            Text(text = "+", fontSize = 30.sp, color = Color.Blue)
        }

        if (showCalendar)
            DateSelectionBox(filterCalendar, onDatePicked = {
                showCalendar = false
                viewModel.showByDate(filterCalendar)
            })
    }
}

@Composable
fun DashboardFilterMenu(
    expanded: Boolean,
    close: () -> Unit,
    modifier: Modifier,
    allShown: DashboardFilter,
    onShowAllClicked: () -> Unit,
    onShowTodayClicked: () -> Unit,
    onCalendarClicked: () -> Unit

) {
    Box(
        modifier = modifier.wrapContentSize()
    ) {
        DropdownMenu(
            expanded = expanded, onDismissRequest = close
        ) {
            if (allShown == DashboardFilter.ShowAll) DropdownMenuItem(onClick = { onShowTodayClicked(); close() }) { Text("Show Today") }
            else DropdownMenuItem(onClick = { onShowAllClicked(); close() }) { Text("Show All") }
            DropdownMenuItem(onClick = { onCalendarClicked(); close() }) { Text("Calendar") }
        }
    }
}

@Composable
fun DateSelectionBox(calendar: Calendar, onDatePicked: () -> Unit) {
    val context = LocalContext.current

    var selectedDateText by remember { mutableStateOf("") }

// Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val datePicker = DatePickerDialog(
        context, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            onDatePicked()
        }, year, month, dayOfMonth
    )

    // datePicker.datePicker.minDate = calendar.timeInMillis

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        datePicker.show()
    }
}
