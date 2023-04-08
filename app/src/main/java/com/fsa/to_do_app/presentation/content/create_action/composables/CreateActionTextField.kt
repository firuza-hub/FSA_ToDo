package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.presentation.common.composables.functional.RoundCheckbox


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
