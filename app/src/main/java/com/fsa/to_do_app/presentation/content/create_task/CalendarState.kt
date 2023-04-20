package com.fsa.to_do_app.presentation.content.create_task

import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.util.DateFormatter.month_date
import com.fsa.to_do_app.util.getDayOfMonth
import com.fsa.to_do_app.util.getDayOfWeek
import java.util.*


data class CalendarState(
    val selectedDate: Calendar,
    val year: Int,
    val month: Int,
    val monthName: String,
    val daysRange: List<CalendarDay>
) {
    companion object {
        private fun getMonthDays(calendar: Calendar, tasks: List<TaskModel>): List<CalendarDay> {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val firstDayOfMonthWeekDay = calendar.getDayOfWeek()
            val lastDayOfMonth = calendar.getActualMaximum(Calendar.DATE)

            val currentMonth = mutableListOf<CalendarDay>()
            for (i in 1 until firstDayOfMonthWeekDay) currentMonth.add(CalendarDay(-1))
            for (i in 1 until lastDayOfMonth) currentMonth.add(CalendarDay(i, tasks.filter { it.date?.getDayOfMonth() == i }))

            return currentMonth
        }

        fun setCalendar(cal: Calendar, tasks: List<TaskModel>): CalendarState{
                return CalendarState(
                    selectedDate = cal,
                    year = cal.get(Calendar.YEAR),
                    month = cal.get(Calendar.MONTH),
                    monthName = month_date.format(cal.time),
                    daysRange = getMonthDays(cal.clone() as Calendar, tasks)
                )
            }

    }
}

data class CalendarDay(
    val num: Int,
    val tasks: List<TaskModel> = emptyList()
)

