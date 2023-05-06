package com.fsa.to_do_app.presentation.content.edit_task

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.common.topBorder
import com.fsa.to_do_app.presentation.content.create_task.ActionProperty
import com.fsa.to_do_app.presentation.content.create_task.composables.TaskBottomToolbar
import com.fsa.to_do_app.presentation.content.create_task.composables.TaskTextField
import com.fsa.to_do_app.presentation.content.create_task.composables.TaskToolBar
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTaskScreen(
    navigateBack: () -> Unit,
    viewModel: EditTaskViewModel = koinViewModel(),
    redirectToPermissionSettings: () -> Unit,
    requestNotificationPermission: () -> Boolean
) {
    val task by viewModel.task.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val calendar by viewModel.calendar.collectAsState()
    val context = LocalContext.current

    val hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestNotificationPermission()
            } else true
        )
    }
    LaunchedEffect(key1 = true) {
        viewModel.validationErrors.collect {
            if (it.contentError.isNullOrBlank().not()) {
                Toast.makeText(context, it.contentError, Toast.LENGTH_SHORT).show()
            }
        }
    }
    var expandPropertyBox by remember {
        mutableStateOf(false)
    }

    var propertyBoxToShow by remember {
        mutableStateOf(ActionProperty.CATEGORY)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val msg = stringResource(R.string.msg_savedSuccessfully)
    if (!loading) {
        Column(Modifier.fillMaxSize()) {
            TaskToolBar(cancel = navigateBack, save = {
                viewModel.save {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    navigateBack()
                }

            }, onAreaClicked = {
                expandPropertyBox = false
                keyboardController?.hide()
                focusManager.clearFocus()
            }, showCancel = false)

            TaskTextField(
                task.content,
                viewModel::onContentChange,
                modifier = Modifier.weight(1f),
                keyboardController,
                onKeyboardShown = { expandPropertyBox = false }
            )

            if (!hasNotificationPermission) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor ="d41939".hexToColor().copy(0.3f),
                    elevation = 0.dp
                ) {
                    Row( modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)) {
                        Text(text = stringResource(R.string.msg_allowNotification), style = MaterialTheme.typography.body2)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = stringResource(R.string.btn_allow), style = MaterialTheme.typography.caption, color = Color.Blue, modifier = Modifier.noRippleClickable { redirectToPermissionSettings() })
                    }
                }
            }
            TaskBottomToolbar(
                modifier = Modifier
                    .topBorder(1.dp, Color.LightGray)
                    .wrapContentHeight(unbounded = true),
                task.category,
                categories,
                task.timeSet,
                task.date,
                expandPropertyBox,
                propertyBoxToShow,
                onSelectedCategoryClicked = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (propertyBoxToShow == ActionProperty.CATEGORY)
                        expandPropertyBox = !expandPropertyBox
                    else {
                        propertyBoxToShow = ActionProperty.CATEGORY
                        if (!expandPropertyBox)
                            expandPropertyBox = true
                    }
                },
                onTimeClicked = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (propertyBoxToShow == ActionProperty.TIME)
                        expandPropertyBox = !expandPropertyBox
                    else {
                        propertyBoxToShow = ActionProperty.TIME
                        if (!expandPropertyBox)
                            expandPropertyBox = true
                    }

                },
                onCalendarClicked = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (propertyBoxToShow == ActionProperty.DATE)
                        expandPropertyBox = !expandPropertyBox
                    else {
                        propertyBoxToShow = ActionProperty.DATE
                        if (!expandPropertyBox)
                            expandPropertyBox = true
                    }
                },
                onCategorySelected = viewModel::selectCategory,
                onDateSelected = viewModel::selectDate,
                calendar = calendar,
                onMonthUp = viewModel::onMonthUp,
                onMonthDown = viewModel::onMonthDown,
                onTimePicked = viewModel::selectTime,
                onTimeResetCLicked = viewModel::resetTime
            )
        }
    }
}