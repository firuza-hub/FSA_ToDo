package com.fsa.to_do_app.presentation.content.edit_category_list.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fsa.to_do_app.R
import com.fsa.to_do_app.presentation.common.composables.shapes.CircleShape
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.noRippleClickable
import com.fsa.to_do_app.presentation.common.toHexString

@Composable
fun CategoryCreationDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    newCategoryName: String,
    newCategoryColor: Color,
    updateCategoryName: (String) -> Unit,
    updateCategoryColor: (Color) -> Unit,
    createNewCategory: () -> Unit,
    colors: List<Color>
) {
    if (showDialog) {

        var colorPickerOpen by rememberSaveable { mutableStateOf(false) }

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 36.dp, bottom = 8.dp)
                            .border(2.dp, newCategoryColor, RoundedCornerShape(10.dp))
                            .padding(8.dp)
                    ) {
                        BasicTextField(
                            value = newCategoryName,
                            onValueChange = updateCategoryName,
                            maxLines = 1,
                            cursorBrush = SolidColor(Color.LightGray),
                            textStyle = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth(0.7f),
                            decorationBox = {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                ) {
                                    if (newCategoryName.isEmpty())
                                        Text(
                                            text = stringResource(R.string.hint_name),
                                            style = MaterialTheme.typography.body1,
                                            color = Color.LightGray
                                        )
                                    it()
                                }
                            }
                        )
                        CircleShape(
                            color = newCategoryColor,
                            modifier = Modifier
                                .noRippleClickable { colorPickerOpen = true }
                                .padding(horizontal = 8.dp),
                            circleSize = 24.dp,
                            borderStroke = BorderStroke(1.dp, Color.Black.copy(0.1f))
                        )
                    }
                    Column(Modifier.fillMaxWidth()) {
                        Text(text = stringResource(R.string.btn_save), color = Color.Blue,
                            modifier = Modifier
                                .noRippleClickable { createNewCategory();}
                                .padding(vertical = 16.dp, horizontal = 32.dp)
                                .align(Alignment.End)
                        )
                    }
                }
            }
        }


        if (colorPickerOpen) {
            ColorDialog(
                colorList = colors,
                onDismiss = { colorPickerOpen = false },
                currentlySelected = newCategoryColor,
                onColorSelected = {
                    updateCategoryColor(it)
                }
            )
        }
    }
}