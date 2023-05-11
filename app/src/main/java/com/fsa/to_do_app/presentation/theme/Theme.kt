package com.fsa.to_do_app.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    background = Color.White,
    onBackground = Color.Black,
    error = Color.Red,
    secondary = Blue,
    primaryVariant = Color.LightGray,
    primary = Color.White
)

private val DarkColorPalette = darkColors(
    background = DarkBlue,
    onBackground = Color.White,
    error = Color.Red,
    secondary = Blue,
    primaryVariant = Color.LightGray,
    primary = Color.White
)

@Composable
fun ToDoAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
