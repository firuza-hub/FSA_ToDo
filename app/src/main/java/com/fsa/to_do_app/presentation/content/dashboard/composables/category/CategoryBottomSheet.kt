package com.fsa.to_do_app.presentation.content.dashboard.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.presentation.common.composables.shapes.DragHandle
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.content.dashboard.DashboardFilter
import com.fsa.to_do_app.util.getHintOnBackground
import com.fsa.to_do_app.util.getTextOnBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryBottomSheet(
    modifier:Modifier,
    actionsByCategory: List<TaskModel>,
    category: CategoryModel,
    modalSheetState: ModalBottomSheetState,
    onActionChecked: (id: Int, checked: Boolean) -> Unit,
    allShown: DashboardFilter
) {
    ModalBottomSheetLayout(
        modifier = modifier.padding(top = 70.dp),
        sheetState = modalSheetState,
        scrimColor = Color.Transparent,
        sheetBackgroundColor = category.colorCode.hexToColor(),
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                DragHandle(
                    color = "252A31".hexToColor(alpha = 0.2f),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp)) {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.h1,
                            color = category.colorCode.hexToColor().getTextOnBackground()
                        )
                        Text(
                            text = buildString {
                                append(category.numberOfActions)
                                append(stringResource(R.string.task_s))
                            },
                            modifier = Modifier.padding(top = 2.dp),
                            style = MaterialTheme.typography.body2,
                            color = category.colorCode.hexToColor().getHintOnBackground()
                        )
                    }
                    Tasks(
                        tasks = actionsByCategory,
                        modifier = Modifier,
                        onTaskChecked = onActionChecked,
                        showCategory = false,
                        allShown = allShown,
                        circleColor = category.colorCode.hexToColor().getHintOnBackground()
                    )
                }

            }
        }
    ) {
    }

}