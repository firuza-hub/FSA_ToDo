package com.fsa.to_do_app.presentation.content.create_action.composables

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.presentation.common.topBorder
import com.fsa.to_do_app.presentation.content.create_action.ActionProperty
import com.fsa.to_do_app.presentation.content.create_action.CreateActionViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateActionScreen(
    navigateBack: () -> Boolean,
    viewModel: CreateActionViewModel = koinViewModel()
) {
    val action by viewModel.action.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val context = LocalContext.current

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

    Column(Modifier.fillMaxSize()) {
        CreateActionToolbar(cancel = navigateBack, save = {
            viewModel.save {
                Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
                navigateBack()
            }

        })
        CreateActionTextField(
            action.content,
            viewModel::onContentChange,
            modifier = Modifier.weight(1f)
        )

        CreateActionBottomToolbar(
            modifier = Modifier
                .topBorder(1.dp, Color.LightGray)
                .wrapContentHeight(unbounded = true),
            action.category,
            categories,
            action,
            expandPropertyBox,
            propertyBoxToShow,
            onSelectedCategoryClicked =
            {
                if (propertyBoxToShow == ActionProperty.CATEGORY)
                    expandPropertyBox = !expandPropertyBox
                else {
                    propertyBoxToShow = ActionProperty.CATEGORY
                    if (!expandPropertyBox)
                        expandPropertyBox = true
                }
            },
            onTimeClicked = {
                if (propertyBoxToShow == ActionProperty.TIME)
                    expandPropertyBox = !expandPropertyBox
                else {
                    propertyBoxToShow = ActionProperty.TIME
                    if (!expandPropertyBox)
                        expandPropertyBox = true
                }
            },
            onCalendarClicked = {
                if (propertyBoxToShow == ActionProperty.DATE)
                    expandPropertyBox = !expandPropertyBox
                else {
                    propertyBoxToShow = ActionProperty.DATE
                    if (!expandPropertyBox)
                        expandPropertyBox = true
                }
            },

            onCategorySelected = viewModel::selectCategory,
            onDateSelected = viewModel::selectDate
        )
    }
}