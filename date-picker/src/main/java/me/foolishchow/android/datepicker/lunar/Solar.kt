package me.foolishchow.android.datepicker.lunar

import me.foolishchow.android.datepicker.Utils
import java.util.*


/**
 * https://github.com/isee15/Lunar-Solar-Calendar-Converter
 * Description:
 * Author: foolishchow
 * Date: 2021/05/12 2:08 PM
 */

/**
 * 公历
 */
class Solar
@JvmOverloads
constructor(
        @JvmField
        var solarDay: Int = 0,
        @JvmField
        var solarMonth: Int = 0,
        @JvmField
        var solarYear: Int = 0
) {
    fun toDate(): Date {
        return Utils.asDate(solarYear, solarMonth, solarDay)
    }
}
