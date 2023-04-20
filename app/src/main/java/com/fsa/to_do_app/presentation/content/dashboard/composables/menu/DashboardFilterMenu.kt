package com.fsa.to_do_app.presentation.content.dashboard.composables.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter


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
            if (allShown == DashboardFilter.ShowAll) DropdownMenuItem(onClick = { onShowTodayClicked(); close() }) {
                Text(
                    "Show Today"
                )
            }
            else DropdownMenuItem(onClick = { onShowAllClicked(); close() }) { Text("Show All") }
            DropdownMenuItem(onClick = { onCalendarClicked(); close() }) { Text("Calendar") }
        }
    }
}