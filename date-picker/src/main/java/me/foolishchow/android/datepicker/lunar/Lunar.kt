package me.foolishchow.android.datepicker.lunar

import me.foolishchow.android.datepicker.utils.Utils
import java.util.*

/**
 * 阴历 农历
 * origin: [Lunar-Solar-Calendar](https://github.com/isee15/Lunar-Solar-Calendar-Converter)
 * file: [LunarSolarConverter.java](https://github.com/isee15/Lunar-Solar-Calendar-Converter/blob/master/Java/cn/z/Lunar.java)
 */
open class Lunar {
    @JvmField
    var lunarYear: Int = 0

    @JvmField
    var lunarMonth: Int = 0

    @JvmField
    var lunarDay: Int = 0

    @JvmField
    var isLeap: Boolean = false

    @JvmField
    var hour: Int = 0

    @JvmField
    var minute: Int = 0

    @JvmField
    var second: Int = 0

    @JvmOverloads
    fun setTime(hourOfDay: Int = 0, minute: Int = 0, second: Int = 0) {
        this.hour = hourOfDay
        this.minute = minute
        this.second = second
    }

    fun setTime(lunar: Lunar) {
        this.hour = lunar.hour
        this.minute = lunar.minute
        this.second = lunar.second
    }

    fun getYearName(): String {
        return LunarDate.getLunarYearText(lunarYear)
    }

    fun getMonthName(): String {
        return LunarDate.getLunarMonthText(this)
    }

    fun getDayName(): String {
        return LunarDate.getChinaDate(lunarDay)
    }

    fun getMonthWithLeap(): Int {
        if (isLeap) return lunarMonth + 1
        val leapMonth = LunarDate.leapMonth(lunarYear)
        return if (leapMonth == 0) {
            lunarMonth
        } else {
            if (leapMonth > lunarMonth) {
                lunarMonth
            } else {
                lunarMonth + 1
            }
        }
    }


    fun toDate(): Date {
        val solar = LunarSolarConverter.LunarToSolar(this)
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = solar.solarYear
        calendar[Calendar.MONTH] = solar.solarMonth - 1
        calendar[Calendar.DAY_OF_MONTH] = solar.solarDay
        calendar[Calendar.HOUR_OF_DAY] = solar.hour
        calendar[Calendar.MINUTE] = solar.minute
        calendar[Calendar.SECOND] = solar.second
        return Date(calendar.timeInMillis)
    }


    companion object {
        @JvmStatic
        fun from(calendar: Calendar): Lunar {
            val lunar = LunarSolarConverter.SolarToLunar(
                    Solar(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH),
                    )
            )
            lunar.hour = calendar.get(Calendar.HOUR_OF_DAY)
            lunar.minute = calendar.get(Calendar.MINUTE)
            lunar.second = calendar.get(Calendar.SECOND)
            return lunar
        }

        @JvmStatic
        fun from(date: Date): Lunar {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date.time
            return from(calendar)
        }


        @JvmOverloads
        @JvmStatic
        fun from(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int = 0, minute: Int = 0, second: Int = 0): Lunar {
            return from(Utils.asCalendar(year, month, dayOfMonth, hourOfDay, minute, second))
        }

        fun fromLunarWithLeap(year: Int, month: Int, day: Int): Lunar {
            val lunar = Lunar()
            val leapMonth = LunarDate.leapMonth(year)
            lunar.lunarYear = year
            lunar.lunarDay = day
            if (leapMonth == 0) {
                lunar.isLeap = false
                lunar.lunarMonth = month
            } else {
                lunar.isLeap = month == leapMonth + 1
                if (leapMonth < month) {
                    lunar.lunarMonth = month - 1
                } else {
                    lunar.lunarMonth = month
                }
            }
            return lunar
        }
    }

}
