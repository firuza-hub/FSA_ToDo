package com.fsa.to_do_app.presentation.content.create_action

import com.fsa.to_do_app.util.DateFormatter.month_date
import com.fsa.to_do_app.util.getDayOfWeek
import java.util.*


data class CalendarState(
    val selectedDate: Calendar,
    val year: Int,
    val month: Int,
    val monthName: String,
    val daysRange: List<Int>
) {
    companion object {
        private fun getMonthDays(calendar:Calendar): List<Int> {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayOfMonthWeekDay = calendar.getDayOfWeek()
            val lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE)

            val currentMonth = mutableListOf<Int>()
            for (i in 1 until firstDayOfMonthWeekDay) currentMonth.add(-1)
            for (i in 1 until lastDayOfMonth) currentMonth.add(i)

            return currentMonth
        }

        fun setCalendar(cal: Calendar): CalendarState{
                return CalendarState(
                    selectedDate = cal,
                    year = cal.get(Calendar.YEAR),
                    month = cal.get(Calendar.MONTH),
                    monthName = month_date.format(cal.time),
                    daysRange = getMonthDays(cal.clone() as Calendar)
                )
            }

    }
}
