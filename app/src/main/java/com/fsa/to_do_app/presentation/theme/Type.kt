package com.fsa.to_do_app.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.fsa.to_do_app.R


val SFPro = FontFamily(
    Font(R.font.sfpro_display_regular, FontWeight.Normal),
    Font(R.font.sfpro_display_medium, FontWeight.Medium),
    Font(R.font.sfpro_display_bold, FontWeight.Bold),
    Font(R.font.sfpro_display_semibold_italic, FontWeight.Medium, FontStyle.Italic)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    body1 = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.Gray
    ),
    subtitle1 = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.LightGray
    ),
    h3 = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    caption = TextStyle(
        fontFamily = SFPro,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
        color = Color.Blue,
        textDecoration = TextDecoration.Underline

    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)