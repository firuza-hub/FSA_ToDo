package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
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
            cursorBrush = SolidColor(Color.LightGray),
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { if (it.hasFocus) onKeyboardShown() }
                .padding(16.dp),
            textStyle = MaterialTheme.typography.body1
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
                            text = "What do you want to do?",
                            style = MaterialTheme.typography.body1,
                            color = Color.LightGray
                        )
                    it()
                }
            }
        }
    }
}
