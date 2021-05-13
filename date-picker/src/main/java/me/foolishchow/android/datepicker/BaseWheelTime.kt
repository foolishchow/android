package me.foolishchow.android.datepicker

import android.view.View
import com.contrarywind.view.WheelView
import me.foolishchow.android.datepicker.options.DatePickerOption
import me.foolishchow.android.datepicker.validator.IDateValidator
import java.util.*

/**
 * Description:
 * Author: foolishchow
 * Date: 2021/05/12 11:12 AM
 */

open class BaseWheelTime<T : IDateValidator>(
        protected val mYearWheel: WheelView? = null,
        protected val mMonthWheel: WheelView? = null,
        protected val mDayWheel: WheelView? = null,
        protected val mHourWheel: WheelView? = null,
        protected val mMinuteWheel: WheelView? = null,
        protected val mSecondWheel: WheelView? = null
) {

    protected var mOnDatePickerSelectListener: OnDatePickerSelectListener? = null
    protected lateinit var mValidator: T
    private fun toggleVisible(wheelView: WheelView?, isVisible: Boolean) {
        if (wheelView != null) {
            wheelView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    protected val isYearVisible: Boolean
        get() = mYearWheel != null && DateStyle.CONFIG[mDateStyle][0]
    protected val isMonthVisible: Boolean
        get() = mMonthWheel != null && DateStyle.CONFIG[mDateStyle][1]
    protected val isDayVisible: Boolean
        get() = mDayWheel != null && DateStyle.CONFIG[mDateStyle][2]
    protected val isHourVisible: Boolean
        get() = mHourWheel != null && DateStyle.CONFIG[mDateStyle][3]
    protected val isMinuteVisible: Boolean
        get() = mMinuteWheel != null && DateStyle.CONFIG[mDateStyle][4]
    protected val isSecondVisible: Boolean
        get() = mSecondWheel != null && DateStyle.CONFIG[mDateStyle][5]

    @DateStyle.Style
    protected var mDateStyle = DateStyle.STYLE_DATE
    private fun setStyle(@DateStyle.Style style: Int) {
        mDateStyle = style
        val displayConfig = DateStyle.CONFIG[mDateStyle]
        toggleVisible(mYearWheel, displayConfig[0])
        toggleVisible(mMonthWheel, displayConfig[1])
        toggleVisible(mDayWheel, displayConfig[2])
        toggleVisible(mHourWheel, displayConfig[3])
        toggleVisible(mMinuteWheel, displayConfig[4])
        toggleVisible(mSecondWheel, displayConfig[5])
    }

    open fun setOption(option: DatePickerOption) {
        mOnDatePickerSelectListener = option.onDatePickerChangeListener
        setStyle(option.style)
        mValidator.setRangeDate(option.rangeStart, option.rangeEnd)
        setSelected(option.selected)
    }


    private fun setSelected(calendar: Calendar) {
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val second = calendar[Calendar.SECOND]
        mValidator.setSelected(year, month, day, hour, minute, second)
    }


}