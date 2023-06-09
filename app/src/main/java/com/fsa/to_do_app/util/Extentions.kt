package com.fsa.to_do_app.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.*
import com.fsa.to_do_app.presentation.theme.Teal200
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun Date.getTimeShort(): String = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(this)
fun Date.getDateShort(): String = SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(this)

fun Calendar.getDayOfWeek(): Int {
    var dayOfWeek: Int = this.get(Calendar.DAY_OF_WEEK) - 1
    if (dayOfWeek == 0) dayOfWeek = 7
    return dayOfWeek
}

fun Date.getDayOfMonth(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.DAY_OF_MONTH)
}

fun Int.intToAmPm(): String {
    return if (this == 1) "PM"
    else "AM"
}

fun String.stringToAmPm(): Int {
    return if (this == "PM") 1
    else 0
}

fun Color.getTextOnBackground(): Color {
    val alpha =  android.graphics.Color.alpha(this.toArgb())
    val contrastWhiteText = ColorUtils.calculateContrast(
        Color.White.toArgb(),
        this.toArgb()
    )
    return if (contrastWhiteText > 1.5f) Color.White else Color.Black
}
fun Color.getHintOnBackground(): Color {
    val contrastWhiteText = ColorUtils.calculateContrast(
        Color.White.toArgb(),
        this.toArgb()
    )
    return if (contrastWhiteText > 1.5f) Color.White.copy(0.5f) else Color.Black.copy(0.5f)
}

