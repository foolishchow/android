package me.foolishchow.android.datepicker.utils

import java.util.*

/**
 * Description:
 * Author: foolishchow
 * Date: 19/12/2020 3:55 PM
 */
object Utils {
    @JvmOverloads
    fun asCalendar(
            year: Int, month: Int, dayOfMonth: Int,
            hourOfDay: Int = 0, minute: Int = 0, second: Int = 0
    ): Calendar {
        val result = Calendar.getInstance()
        result[year, month - 1, dayOfMonth, hourOfDay, minute] = second
        return result
    }

    fun asCalendar(date: Date?): Calendar {
        val result = Calendar.getInstance()
        result.time = date
        return result
    }

    @JvmStatic
    @JvmOverloads
    fun asDate(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int = 0, minute: Int = 0, second: Int = 0): Date {
        return Date(asCalendar(year, month, dayOfMonth, hourOfDay, minute, second).timeInMillis)
    }

    @JvmStatic
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }
}