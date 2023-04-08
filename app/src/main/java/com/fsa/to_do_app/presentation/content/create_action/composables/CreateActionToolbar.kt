package com.fsa.to_do_app.presentation.content.create_action.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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