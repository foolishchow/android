package me.foolishchow.android.datepicker.options

import me.foolishchow.android.datepicker.DateStyle
import me.foolishchow.android.datepicker.OnDatePickerSelectListener
import me.foolishchow.android.datepicker.Utils
import java.util.*

/**
 * Description:
 * Author: foolishchow
 * Date: 18/12/2020 3:42 PM
 */
@Suppress("UNUSED")
open class DatePickerOption {
    var rangeStart: Calendar = sMinRangeStart
        internal set
    var rangeEnd: Calendar = sMaxRangeEnd
        internal set

    @get:DateStyle.Style
    @DateStyle.Style
    var style = DateStyle.STYLE_DATE
        internal set
    var selected: Calendar = Calendar.getInstance()
        internal set
    private var mOnDatePickerChangeListener: OnDatePickerSelectListener? = null


    @JvmOverloads
    open fun setRangeStart(year: Int, month: Int, dayOfMonth: Int,
                           hourOfDay: Int = 0, minute: Int = 0, second: Int = 0) {
        setRangeStart(Utils.asCalendar(year, month, dayOfMonth, hourOfDay, minute, second))
    }

    open fun setRangeStart(rangeStart: Date?) {
        setRangeStart(if (rangeStart == null) null else Utils.asCalendar(rangeStart))
    }

    open fun setRangeStart(start: Calendar?) {
        val rangeStart = when {
            start == null -> {
                sMinRangeStart
            }
            start.timeInMillis < sMinRangeStart.timeInMillis -> {
                sMinRangeStart
            }
            else -> {
                start
            }
        }
        check(rangeEnd.timeInMillis >= rangeStart.timeInMillis) { "range start is later than range end !" }
        this.rangeStart = rangeStart
    }

    @JvmOverloads
    open fun setRangeEnd(year: Int, month: Int, dayOfMonth: Int,
                         hourOfDay: Int = 0, minute: Int = 0, second: Int = 0
    ) {
        setRangeEnd(Utils.asCalendar(year, month, dayOfMonth, hourOfDay, minute, second))
    }

    open fun setRangeEnd(rangeEnd: Date?) {
        setRangeEnd(if (rangeEnd == null) null else Utils.asCalendar(rangeEnd))
    }

    open fun setRangeEnd(end: Calendar?) {
        val rangeEnd = when {
            end == null -> {
                sMaxRangeEnd
            }
            end.timeInMillis > sMaxRangeEnd.timeInMillis -> {
                sMaxRangeEnd
            }
            else -> {
                end
            }
        }
        check(rangeStart.timeInMillis <= rangeEnd.timeInMillis) { "range start is later than range end !" }
        this.rangeEnd = rangeEnd
    }

    @JvmOverloads
    fun setSelected(year: Int, month: Int, dayOfMonth: Int,
                    hourOfDay: Int = 0, minute: Int = 0, second: Int = 0) {
        setSelected(Utils.asCalendar(year, month, dayOfMonth, hourOfDay, minute, second))
    }

    open fun setSelected(selected: Date?) {
        this.selected = if (selected == null) Calendar.getInstance() else Utils.asCalendar(selected)
    }

    open fun setSelected(selected: Calendar?) {
        this.selected = selected ?: Calendar.getInstance()
    }

    fun setStyle(@DateStyle.Style style: Int): DatePickerOption {
        this.style = style
        return this
    }

    val onDatePickerChangeListener: OnDatePickerSelectListener
        get() = mOnDatePickerChangeListener!!

    fun setOnDatePickerSelectListener(listener: OnDatePickerSelectListener): DatePickerOption {
        mOnDatePickerChangeListener = listener
        return this
    }

    companion object {
        private val sMinRangeStart = Utils.asCalendar(1900, 1, 1)
        private val sMaxRangeEnd = Utils.asCalendar(2100, 12, 31)
    }
}