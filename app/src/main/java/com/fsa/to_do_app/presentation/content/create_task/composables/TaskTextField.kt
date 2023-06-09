package com.fsa.to_do_app.presentation.content.create_task.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.composables.functional.RoundCheckbox


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskTextField(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardController: SoftwareKeyboardController?,
    onKeyboardShown: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = content,
            onValueChange = onContentChange,
            cursorBrush = SolidColor(MaterialTheme.colors.primaryVariant),
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { if (it.hasFocus) onKeyboardShown() }
                .padding(16.dp),
            textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground)
        ) {
            Row {
                RoundCheckbox(
                    checked = false,
                    enabled = false,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(28.dp)
                )
                Box{

                    if (content.isEmpty())
                        Text(
                            text = stringResource(R.string.hint_whatDoYouWantToDo),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primaryVariant
                        )
                    it()
                }
            }
        }
    }
}
