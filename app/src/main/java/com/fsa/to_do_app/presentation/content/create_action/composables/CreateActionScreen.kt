package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.presentation.common.composables.functional.RoundCheckbox
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.topBorder
import com.fsa.to_do_app.presentation.content.create_action.CreateActionViewModel
import com.fsa.to_do_app.presentation.content.dashboard.composables.Categories
import com.fsa.to_do_app.presentation.content.dashboard.composables.CategoryIndicator
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateActionScreen(
    navigateBack: () -> Boolean,
    viewModel: CreateActionViewModel = koinViewModel()
) {
    val action by viewModel.action.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var expandPropertyBox by remember {
        mutableStateOf(false)
    }

    var propertyBoxToShow by remember {
        mutableStateOf(ActionProperty.CATEGORY)
    }
    Column(Modifier.fillMaxSize()) {
        CreateActionToolbar(cancel = navigateBack, save = {})
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
            expandPropertyBox,
            propertyBoxToShow,
            onSelectedCategoryClicked =
            {
                propertyBoxToShow = ActionProperty.CATEGORY
                expandPropertyBox = !expandPropertyBox
            },
        onCategorySelected = viewModel::selectCategory)
    }
}


@Composable
fun CreateActionToolbar(cancel: () -> Boolean, save: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Cancel", modifier = Modifier.clickable { cancel() }, color = Color.Blue)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Done", modifier = Modifier.clickable { save() }, color = Color.Blue)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateActionTextField(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(modifier = modifier) {
        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .padding(16.dp),
            textStyle = MaterialTheme.typography.body1,
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),

            ) {
            Row {
                RoundCheckbox(
                    checked = false,
                    enabled = false,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(28.dp)
                )
                if (content.isEmpty())
                    Text(
                        text = "What do you want to do?",
                        style = MaterialTheme.typography.body1,
                        color = Color.LightGray
                    )
                else it()
            }
        }
    }
}

@Composable
fun CreateActionBottomToolbar(
    modifier: Modifier,
    category: CategoryModel,
    categories: List<CategoryModel>,
    expandPropertyBox: Boolean,
    propertyBoxToShow: ActionProperty,
    onSelectedCategoryClicked: () -> Unit,
    onCategorySelected: (CategoryModel) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Open Calendar",
            modifier = Modifier.padding(6.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "Open Time Picker",
            modifier = Modifier.padding(6.dp),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.weight(1f))
        SelectedCategory(
            category,
            Modifier
                .align(CenterVertically)
                .clickable { onSelectedCategoryClicked() },
            propertyBoxToShow == ActionProperty.CATEGORY && expandPropertyBox
        )

    }
    if (expandPropertyBox) {
        ActionPropertiesSelectionBox(
            modifier = Modifier.height(350.dp),
            categories = categories,
            actionProperty = ActionProperty.CATEGORY,
            onCategorySelected = onCategorySelected
        )
    }
}


@Composable
fun SelectedCategory(
    category: CategoryModel,
    modifier: Modifier,
    isExpanded: Boolean
) {
    Row(modifier = modifier) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.subtitle1,
            color = if (isExpanded) Color.Blue else Color.LightGray
        )
        CategoryIndicator(
            category.colorCode.hexToColor(),
            Modifier
                .align(CenterVertically)
                .wrapContentWidth(unbounded = true)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ActionPropertiesSelectionBox(
    modifier: Modifier,
    actionProperty: ActionProperty,
    categories: List<CategoryModel>,
    onCategorySelected: (CategoryModel) -> Unit
) {
    Box(modifier = modifier.padding(16.dp)) {
        when (actionProperty) {
            ActionProperty.CATEGORY -> {
                Categories(
                    categories = categories,
                    modifier = Modifier,
                    onCategorySelected = onCategorySelected
                )
            }
            ActionProperty.DATE -> {}
            ActionProperty.TIME -> {}
        }
    }
}

enum class ActionProperty {
    CATEGORY,
    DATE,
    TIME
}