package me.foolishchow.android.datepicker.lunar

import me.foolishchow.android.datepicker.utils.Utils
import java.util.*

/**
 * 公历
 * origin: [Lunar-Solar-Calendar](https://github.com/isee15/Lunar-Solar-Calendar-Converter)
 * file: [LunarSolarConverter.java](https://github.com/isee15/Lunar-Solar-Calendar-Converter/blob/master/Java/cn/z/Solar.java)
 */
class Solar
@JvmOverloads
constructor(
        @JvmField
        var solarYear: Int = 0,
        @JvmField
        var solarMonth: Int = 0,
        @JvmField
        var solarDay: Int = 0
) {
    fun toDate(): Date {
        return Utils.asDate(solarYear, solarMonth, solarDay)
    }

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
}
