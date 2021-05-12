package me.foolishchow.android.datepicker.lunar

/**
 * 阴历
 */
class Lunar
@JvmOverloads
constructor(
        @JvmField
        var isLeap: Boolean = false,
        @JvmField
        var lunarDay: Int = 0,
        @JvmField
        var lunarMonth: Int = 0,
        @JvmField
        var lunarYear: Int = 0
) {

}