package me.foolishchow.android.datepicker.lunar

import java.util.*

/**
 * 阴历
 */
class Lunar
@JvmOverloads
constructor(
        @JvmField
        var lunarYear: Int = 0,
        @JvmField
        var lunarMonth: Int = 0,
        @JvmField
        var lunarDay: Int = 0,
) {
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

    override fun toString(): String {
        return "Lunar(lunarYear=$lunarYear, lunarMonth=$lunarMonth, lunarDay=$lunarDay, hour=$hour, minute=$minute, second=$second)"
    }

    fun toDate(): Date {
        val solar = LunarSolarConverter.LunarToSolar(this)
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = solar.solarYear
        calendar[Calendar.MONTH] = solar.solarMonth-1
        calendar[Calendar.DAY_OF_MONTH] = solar.solarDay
        calendar[Calendar.HOUR_OF_DAY] = solar.hour
        calendar[Calendar.MINUTE] = solar.minute
        calendar[Calendar.SECOND] = solar.second
        return  Date(calendar.timeInMillis)
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


    }

}