package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.presentation.content.create_action.CreateActionViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CreateActionScreen(
    navigateBack: () -> Boolean,
    viewModel: CreateActionViewModel = koinViewModel()
) {
    val action by viewModel.action.collectAsState()
    Column(Modifier.fillMaxSize()) {
        CreateActionToolbar(cancel = navigateBack, save = {})
        CreateActionTextField(action.content, viewModel::onContentChange)
        CreateActionBottomToolbar()
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

@Composable
fun CreateActionTextField(content: String, onContentChange: (String) -> Unit) {
    BasicTextField(
        value = content,
        onValueChange = onContentChange,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        textStyle = MaterialTheme.typography.body2

    )
}

@Composable
fun CreateActionBottomToolbar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //calendar
        //clock
        //spacer
        //category bottom heet trigger
    }
}