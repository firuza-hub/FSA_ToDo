package com.fsa.to_do_app.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    val month_date = SimpleDateFormat("MMMM")

    fun parseDateTime(date: Date): String {
        val format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
        return format.format(date).toString()
    }
}