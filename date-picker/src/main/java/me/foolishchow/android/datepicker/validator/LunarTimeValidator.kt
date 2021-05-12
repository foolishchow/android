package me.foolishchow.android.datepicker.validator

import me.foolishchow.android.datepicker.Utils
import me.foolishchow.android.datepicker.lunar.Lunar
import me.foolishchow.android.datepicker.lunar.LunarDate
import me.foolishchow.android.datepicker.lunar.LunarSolarConverter
import me.foolishchow.android.datepicker.lunar.Solar
import java.util.*


/**
 * Description:
 * Author: foolishchow
 * Date: 2021/05/12 1:25 PM
 */
class LunarTimeValidator : IDateValidator {

    private val mRangeStart = intArrayOf(1990, 1, 1, 0, 0, 0)
    private val mRangeEnd = intArrayOf(2100, 12, 31, 23, 59, 59)
    private val mSelected = intArrayOf(1990, 1, 1, 0, 0, 0)
    private val mRangeCurrent = arrayOf(
            intArrayOf(1990, 2100),
            intArrayOf(1, 12),
            intArrayOf(1, 31),
            intArrayOf(0, 23),
            intArrayOf(0, 59),
            intArrayOf(0, 59)
    )

    private fun updateDateArray(array: IntArray, calendar: Lunar) {
        array[0] = calendar.lunarYear
        array[1] = calendar.lunarMonth
        array[2] = calendar.lunarDay
        array[3] = calendar.hour
        array[4] = calendar.minute
        array[5] = calendar.second
    }

    override fun setRangeDate(startDate: Calendar, endDate: Calendar) {
        updateDateArray(mRangeStart, Lunar.from(startDate))
        updateDateArray(mRangeEnd, Lunar.from(endDate))
        mRangeCurrent[0][0] = mRangeStart[0]
        mRangeCurrent[0][1] = mRangeEnd[0]
    }


    override fun setSelected(
            year: Int, month: Int, dayOfMonth: Int,
            hourOfDay: Int, minute: Int, second: Int
    ) {

        val lunar = LunarSolarConverter.SolarToLunar(Solar(year, month, dayOfMonth))
        lunar.setTime(hourOfDay, minute, second)
        updateDateArray(mSelected, lunar)
        Validate()
    }


    private var mValidatedListener: ValidatedListener? = null

    override fun setValidatedListener(listener: ValidatedListener) {
        this.mValidatedListener = listener
    }

    val time: Lunar
        get() {
            val lunar = Lunar(mSelected[0], mSelected[1], mSelected[2])
            lunar.setTime(mSelected[3], mSelected[4], mSelected[5])
            return lunar
        }

    fun YearChange(year: Int) {
        mSelected[0] = year
        Validate()
    }

    fun MonthChange(month: Int) {
        mSelected[1] = month
        Validate()
    }

    fun DayChange(dayOfMonth: Int) {
        mSelected[2] = dayOfMonth
        Validate()
    }

    fun HourChange(hourOfDay: Int) {
        mSelected[3] = hourOfDay
        Validate()
    }

    fun MinuteChange(minute: Int) {
        mSelected[4] = minute
        Validate()
    }

    fun SecondChange(second: Int) {
        mSelected[5] = second
        Validate()
    }

    fun Validate() {
        val year = mSelected[0]
        var month = mSelected[1]
        var dayOfMonth = mSelected[2]
        var hourOfDay = mSelected[3]
        var minute = mSelected[4]
        val second = mSelected[5]

        val monthRange = intArrayOf(1, 12)
        if (year == mRangeStart[0]) {
            monthRange[0] = mRangeStart[1]
        }
        if (year == mRangeEnd[0]) {
            monthRange[1] = mRangeEnd[1]
        }
        if (month < monthRange[0]) {
            month = monthRange[0]
            mSelected[1] = month
        } else if (month > monthRange[1]) {
            month = monthRange[1]
            mSelected[1] = month
        }
        mRangeCurrent[1] = monthRange

        //endregion

        //region dayOfMonth
        val dayOfMonthRange = intArrayOf(1, getMonthDay(year, month))
        if (year == mRangeStart[0] && month == mRangeStart[1]) {
            dayOfMonthRange[0] = mRangeStart[2]
        }
        if (year == mRangeEnd[0] && month == mRangeEnd[1]) {
            dayOfMonthRange[1] = mRangeEnd[2]
        }

        if (dayOfMonth < dayOfMonthRange[0]) {
            dayOfMonth = dayOfMonthRange[0]
            mSelected[2] = dayOfMonth
        } else if (dayOfMonth > dayOfMonthRange[1]) {
            dayOfMonth = dayOfMonthRange[1]
            mSelected[2] = dayOfMonth
        }
        mRangeCurrent[2] = dayOfMonthRange


        //region hourOfDay
        //endregion

        //region hourOfDay
        val hourOfDayRange = intArrayOf(0, 23)
        if (year == mRangeStart[0] && month == mRangeStart[1] && dayOfMonth == mRangeStart[2]) {
            hourOfDayRange[0] = mRangeStart[3]
        }
        if (year == mRangeEnd[0] && month == mRangeEnd[1] && dayOfMonth == mRangeEnd[2]) {
            hourOfDayRange[1] = mRangeEnd[3]
        }
        if (hourOfDay < hourOfDayRange[0]) {
            hourOfDay = hourOfDayRange[0]
            mSelected[3] = hourOfDay
        } else if (hourOfDay > hourOfDayRange[1]) {
            hourOfDay = hourOfDayRange[1]
            mSelected[3] = hourOfDay
        }
        mRangeCurrent[3] = hourOfDayRange
        //endregion

        //region minute
        val minuteRange = intArrayOf(0, 59)
        if (year == mRangeStart[0] && month == mRangeStart[1] && dayOfMonth == mRangeStart[2] && hourOfDay == mRangeStart[3]) {
            minuteRange[0] = mRangeStart[4]
        }
        if (year == mRangeEnd[0] && month == mRangeEnd[1] && dayOfMonth == mRangeEnd[2] && hourOfDay == mRangeEnd[3]) {
            minuteRange[1] = mRangeEnd[4]
        }
        if (minute < minuteRange[0]) {
            minute = minuteRange[0]
            mSelected[4] = minute
        } else if (minute > minuteRange[1]) {
            minute = minuteRange[1]
            mSelected[4] = minute
        }
        mRangeCurrent[4] = minuteRange
        //endregion
        emitValidate()
    }

    private fun emitValidate() {
        val list = arrayOfNulls<ValidateResult>(6)
        for (index in 0..5) {
            list[index] = ValidateResult(mRangeCurrent[index][0], mRangeCurrent[index][1], mSelected[index])
        }
        try {
            mValidatedListener?.onDateTimeValidated(list[0]!!, list[1]!!, list[2]!!, list[3]!!, list[4]!!, list[5]!!)
        } catch (ignored: Exception) {
        }
    }


    private fun getMonthDay(year: Int, month: Int): Int {
        return LunarDate.monthDays(year, month)
    }
}