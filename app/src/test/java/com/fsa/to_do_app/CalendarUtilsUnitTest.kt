package com.fsa.to_do_app

import com.fsa.to_do_app.util.getDayOfMonth
import com.fsa.to_do_app.util.getDayOfWeek
import org.junit.Assert
import org.junit.Test
import java.util.Calendar
import java.util.Date

class CalendarUtilsUnitTest {

    @Test
    fun getDayOfMonth_IsCorrect(){
        val date = Date(1684044227000)//14/05/2023
        val dayOfMonth = date.getDayOfMonth()
        Assert.assertEquals(14, dayOfMonth)
    }
    @Test
    fun getDayOfWeek_IsCorrect(){
        val date = Date(1684044227000)//14/05/2023: Sunday
        val cal = Calendar.getInstance()
        cal.time = date
        val dayOfWeek = cal.getDayOfWeek()
        Assert.assertEquals(7, dayOfWeek)

        cal.add(Calendar.DAY_OF_WEEK, -1)
        val dayOfWeekOneDayLess = cal.getDayOfWeek()
        Assert.assertEquals(6, dayOfWeekOneDayLess)
    }
}