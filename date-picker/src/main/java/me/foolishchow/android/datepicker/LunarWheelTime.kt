package me.foolishchow.android.datepicker

import com.contrarywind.view.WheelView
import me.foolishchow.android.datepicker.adapters.DateWheelAdapter
import me.foolishchow.android.datepicker.adapters.LunarWheelAdapter
import me.foolishchow.android.datepicker.lunar.Lunar
import me.foolishchow.android.datepicker.lunar.LunarUtils
import me.foolishchow.android.datepicker.options.DatePickerOption
import me.foolishchow.android.datepicker.options.LunarDatePickerOption
import me.foolishchow.android.datepicker.validator.*

open class LunarWheelTime
@JvmOverloads
constructor(
        yearWheel: WheelView? = null,
        monthWheel: WheelView? = null,
        dayWheel: WheelView? = null,
        hourWheel: WheelView? = null,
        minuteWheel: WheelView? = null,
        secondWheel: WheelView? = null
) : BaseWheelTime<LunarTimeValidator>(
        yearWheel, monthWheel, dayWheel,
        hourWheel, minuteWheel, secondWheel
), ValidatedListener {


    private val mYearAdapter = LunarWheelAdapter()
    private val mMonthAdapter = LunarWheelAdapter()
    private val mDayAdapter = LunarWheelAdapter()
    private val mHourAdapter = DateWheelAdapter()
    private val mMinuteAdapter = DateWheelAdapter()
    private val mSecondAdapter = DateWheelAdapter()

    init {
        mValidator = LunarTimeValidator()
        mValidator.setValidatedListener(this)
        if (mYearWheel != null) {
            mYearWheel.adapter = mYearAdapter
            mYearWheel.setOnItemSelectedListener { index ->
                val item = mYearAdapter.getItem(index)
                mValidator.YearChange(item.value)
            }
        }
        if (mMonthWheel != null) {
            mMonthWheel.adapter = mMonthAdapter
            mMonthWheel.setOnItemSelectedListener { index ->
                val item = mMonthAdapter.getItem(index)
                mValidator.MonthChange(item.value)
            }
        }
        if (mDayWheel != null) {
            mDayWheel.adapter = mDayAdapter
            mDayWheel.setOnItemSelectedListener { index ->
                val item = mDayAdapter.getItem(index)
                mValidator.DayChange(item.value)
            }
        }
        if (mHourWheel != null) {
            mHourWheel.adapter = mHourAdapter
            mHourWheel.setOnItemSelectedListener { index ->
                val item = mHourAdapter.getItem(index)
                mValidator.HourChange(item.value)
            }
        }
        if (mMinuteWheel != null) {
            mMinuteWheel.adapter = mMinuteAdapter
            mMinuteWheel.setOnItemSelectedListener { index ->
                val item = mMinuteAdapter.getItem(index)
                mValidator.MinuteChange(item.value)
            }
        }
        if (mSecondWheel != null) {
            mSecondWheel.adapter = mSecondAdapter
            mSecondWheel.setOnItemSelectedListener { index ->
                val item = mSecondAdapter.getItem(index)
                mValidator.SecondChange(item.value)
            }
        }
    }


    private var showLeapMonth = true
    fun setOption(option: LunarDatePickerOption) {
        showLeapMonth = option.showLeapMonth
        mValidator.showLeapMonth = option.showLeapMonth
        super.setOption(option)
    }

    open fun getTime(): Lunar {
        return mValidator.time
    }

    override fun onDateTimeValidated(
            year: ValidateResult, month: ValidateResult, dayOfMonth: ValidateResult,
            hourOfDay: ValidateResult, minute: ValidateResult, second: ValidateResult
    ) {
        if (isYearVisible) {
            val rangeChanged = mYearAdapter.rangeChanged(year)
            if (rangeChanged) {
                mYearAdapter.reRange(LunarUtils.getLunarYears(year))
            }
            mYearWheel!!.currentItem = mYearAdapter.getItemIndex(year.current)
        }
        if (isMonthVisible) {
            mMonthAdapter.reRange(LunarUtils.getLunarMonths(
                    month, if (showLeapMonth) year.current else -1
            ))
            mMonthWheel!!.currentItem = mMonthAdapter.getItemIndex(month.current)
        }
        if (isDayVisible) {
            val rangeChanged = mDayAdapter.rangeChanged(dayOfMonth)
            if (rangeChanged) {
                mDayAdapter.reRange(LunarUtils.getLunarDays(dayOfMonth))
            }
            mDayWheel!!.currentItem = mDayAdapter.getItemIndex(dayOfMonth.current)
        }
        if (isHourVisible) {
            mHourAdapter.reRange(hourOfDay.rangeStart, hourOfDay.rangeEnd)
            mHourWheel!!.currentItem = mHourAdapter.getItemIndex(hourOfDay.current)
        }
        if (isMinuteVisible) {
            mMinuteAdapter.reRange(minute.rangeStart, minute.rangeEnd)
            mMinuteWheel!!.currentItem = mMinuteAdapter.getItemIndex(minute.current)
        }
        if (isSecondVisible) {
            mSecondAdapter.reRange(second.rangeStart, second.rangeEnd)
            mSecondWheel!!.currentItem = mSecondAdapter.getItemIndex(second.current)
        }
        mOnDatePickerSelectListener?.onDatePickerSelect()
    }


}