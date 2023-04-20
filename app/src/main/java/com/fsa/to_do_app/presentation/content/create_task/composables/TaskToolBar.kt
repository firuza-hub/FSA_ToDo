package com.fsa.to_do_app.presentation.content.create_task.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fsa.to_do_app.presentation.common.noRippleClickable

@Composable
fun TaskToolBar(cancel: () -> Boolean, save: () -> Unit, onAreaClicked:() -> Unit) {
    Row(
        Modifier.noRippleClickable { onAreaClicked() }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Cancel", modifier = Modifier.noRippleClickable { cancel() }, color = Color.Blue)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Done", modifier = Modifier.noRippleClickable { save() }, color = Color.Blue)
    }
}