package com.fsa.to_do_app.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun Date.getTimeShort(): String = SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(this)

fun Calendar.getDayOfWeek(): Int {
    var dayOfWeek: Int = this.get(Calendar.DAY_OF_WEEK) - 1
    if (dayOfWeek == 0) dayOfWeek = 7

    return dayOfWeek
}