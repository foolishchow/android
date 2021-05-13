package me.foolishchow.android.datepicker.lunar

import me.foolishchow.android.datepicker.data.DateWheelVo
import me.foolishchow.android.datepicker.validator.ValidateResult

/**
 * Description:
 * Author: foolishchow
 * Date: 2021/05/13 11:03 AM
 */
object LunarUtils {

    fun getLunarDays(result: ValidateResult): List<DateWheelVo> {
        return getLunarDays(result.rangeStart, result.rangeEnd)
    }

    fun getLunarDays(start: Int, end: Int): List<DateWheelVo> {
        val days = mutableListOf<DateWheelVo>()
        for (i in start..end) {
            days.add(DateWheelVo(
                    LunarDate.getChinaDate(i),
                    i
            ))
        }
        return days
    }

    fun getLunarYears(result: ValidateResult): List<DateWheelVo> {
        return getLunarYears(result.rangeStart, result.rangeEnd)
    }

    fun getLunarYears(startYear: Int, endYear: Int): List<DateWheelVo> {
        val years = mutableListOf<DateWheelVo>()
        for (year in startYear..endYear) {
            years.add(
                    DateWheelVo(
                            String.format("%d%s", year, LunarDate.getLunarYearText(year)),
                            year
                    )
            )
        }
        return years
    }

    /**
     * 获取当前年的月份
     * 如果year为-1 则不考虑闰月的情况 返回 1，12
     * 如果考虑闰月
     *  有闰月 ->  入 闰2月  变成 1，13
     */
    @JvmOverloads
    fun getLunarMonths(result: ValidateResult, lunarYear: Int = -1): List<DateWheelVo> {
        return getLunarMonths(result.rangeStart, result.rangeEnd, lunarYear)
    }

    /**
     * 如果传入年份
     */
    fun getLunarMonths(start: Int, end: Int, year: Int = -1): List<DateWheelVo> {
        val leapMonth = if (year == -1) -1 else LunarDate.leapMonth(year)
        val months = mutableListOf<DateWheelVo>()
        for (month in start..end) {
            if (leapMonth <= 0) {
                months.add(
                        DateWheelVo(
                                LunarDate.getLunarMonthText(month) + "月",
                                month
                        )
                )
            } else {
                var label: String
                var value: Int
                when {
                    leapMonth >= month -> {
                        label = LunarDate.getLunarMonthText(month) + "月"
                        value = month
                    }
                    leapMonth == month - 1 -> {
                        label = "闰" + LunarDate.getLunarMonthText(month - 1) + "月"
                        value = month
                    }
                    else -> {
                        label = LunarDate.getLunarMonthText(month - 1) + "月"
                        value = month
                    }
                }
                months.add(DateWheelVo(label, value))
            }
        }
        return months
    }
}