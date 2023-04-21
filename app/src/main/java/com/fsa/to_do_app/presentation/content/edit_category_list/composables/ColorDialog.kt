package com.fsa.to_do_app.presentation.content.edit_category_list.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorDialog(
    colorList: List<Color>,
    onDismiss: (() -> Unit),
    currentlySelected: Color,
    onColorSelected: ((Color) -> Unit) // when a colour is picked
) {

    val gridState = rememberLazyGridState()

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.background,
        onDismissRequest = onDismiss,
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState
            ) {
                items(colorList) { color ->
                    // Add a border around the selected colour only
                    var borderWidth = 0.dp
                    if (currentlySelected == color) {
                        borderWidth = 2.dp
                    }

                    ColorPaletteItem(
                        onColorSelected = onColorSelected,
                        color = color, borderWidth = borderWidth, onDismiss =onDismiss
                    )
                }
            }
        },
        confirmButton = {}
    )
}