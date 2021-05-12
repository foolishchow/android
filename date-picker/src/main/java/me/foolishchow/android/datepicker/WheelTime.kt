package me.foolishchow.android.datepicker

import android.view.View
import androidx.annotation.IntDef
import com.contrarywind.view.WheelView
import me.foolishchow.android.datepicker.DateTimeValidator.ValidateResult
import me.foolishchow.android.datepicker.DateTimeValidator.ValidatedListener
import me.foolishchow.android.datepicker.adapters.DateWheelAdapter
import java.util.*

/**
 * 对比原版 WheelTime 主要改变的几个点
 * 1. 数据校验不在此处进行 全部交由[me.foolishchow.android.datepicker.options.DatePickerOption] 处理 所有的数据都确保是validated
 * 2. 控件外部可控 你需要展示什么就初始化什么 不需要的就设置为初始null 不再关心布局
 * 3. 时间range校验流程修改
 */
class WheelTime : ValidatedListener {
    private val mValidator = DateTimeValidator()

    //region 控件
    private var mYearWheel: WheelView? = null
    private var mMonthWheel: WheelView? = null
    private var mDayWheel: WheelView? = null
    private var mHourWheel: WheelView? = null
    private var mMinuteWheel: WheelView? = null
    private var mSecondWheel: WheelView? = null

    @JvmOverloads
    fun setWheels(
            year: DateWheelView?, month: DateWheelView?, day: DateWheelView? = null,
            hour: DateWheelView? = null, minute: DateWheelView? = null, second: DateWheelView? = null
    ) {
        mYearWheel = year
        mMonthWheel = month
        mDayWheel = day
        mHourWheel = hour
        mMinuteWheel = minute
        mSecondWheel = second
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(STYLE_DATE, STYLE_DATE_TIME, STYLE_YEAR_MONTH, STYLE_DATE_HOUR_MINUTE, STYLE_MOMENT)
    annotation class DateStyle

    private fun toggleVisible(wheelView: WheelView?, isVisible: Boolean) {
        if (wheelView != null) {
            wheelView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private val isYearVisible: Boolean
        get() = mYearWheel != null && DISPLAY_CONFIG[mDateStyle][0]
    private val isMonthVisible: Boolean
        get() = mMonthWheel != null && DISPLAY_CONFIG[mDateStyle][1]
    private val isDayVisible: Boolean
        get() = mDayWheel != null && DISPLAY_CONFIG[mDateStyle][2]
    private val isHourVisible: Boolean
        get() = mHourWheel != null && DISPLAY_CONFIG[mDateStyle][3]
    private val isMinuteVisible: Boolean
        get() = mMinuteWheel != null && DISPLAY_CONFIG[mDateStyle][4]
    private val isSecondVisible: Boolean
        get() = mSecondWheel != null && DISPLAY_CONFIG[mDateStyle][5]

    @DateStyle
    private var mDateStyle = STYLE_DATE
    fun setStyle(@DateStyle style: Int) {
        mDateStyle = style
        val displayConfig = DISPLAY_CONFIG[mDateStyle]
        toggleVisible(mYearWheel, displayConfig[0])
        initYearWheel()
        toggleVisible(mMonthWheel, displayConfig[1])
        initMonthWheel()
        toggleVisible(mDayWheel, displayConfig[2])
        initDayWheel()
        toggleVisible(mHourWheel, displayConfig[3])
        initHourWheel()
        toggleVisible(mMinuteWheel, displayConfig[4])
        initMinuteWheel()
        toggleVisible(mSecondWheel, displayConfig[5])
        initSecondWheel()
    }

    private val mYearAdapter = DateWheelAdapter()
    private val mMonthAdapter = DateWheelAdapter()
    private val mDayAdapter = DateWheelAdapter()
    private val mHourAdapter = DateWheelAdapter()
    private val mMinuteAdapter = DateWheelAdapter()
    private val mSecondAdapter = DateWheelAdapter()

    private fun initYearWheel() {
        if (!isYearVisible) return
        mYearAdapter.reRange(1970, 2100)
        // 年
        mYearWheel!!.adapter = mYearAdapter // 设置"年"的显示数据
        mYearWheel!!.setOnItemSelectedListener { index ->
            val item = mYearAdapter.getItem(index)
            mValidator.YearChange(item.value)
        }
    }

    private fun initMonthWheel() {
        if (!isMonthVisible) return
        mMonthAdapter.reRange(1, 12)
        mMonthWheel!!.adapter = mMonthAdapter
        // 添加"月"监听
        mMonthWheel!!.setOnItemSelectedListener { index ->
            val item = mMonthAdapter.getItem(index)
            mValidator.MonthChange(item.value)
        }
    }

    private fun initDayWheel() {
        if (!isDayVisible) return
        mDayAdapter.reRange(1, 31)
        mDayWheel!!.adapter = mDayAdapter
        mDayWheel!!.setOnItemSelectedListener { index ->
            val item = mDayAdapter.getItem(index)
            mValidator.DayChange(item.value)
        }
    }

    private fun initHourWheel() {
        if (!isHourVisible) return
        mHourAdapter.reRange(1, 24)
        mHourWheel!!.adapter = mHourAdapter
        mHourWheel!!.setOnItemSelectedListener { index ->
            val item = mHourAdapter.getItem(index)
            mValidator.HourChange(item.value)
        }
    }

    private fun initMinuteWheel() {
        if (!isMinuteVisible) return
        mMinuteAdapter.reRange(1, 60)
        mMinuteWheel!!.adapter = mMinuteAdapter
        mMinuteWheel!!.setOnItemSelectedListener { index ->
            val item = mMinuteAdapter.getItem(index)
            mValidator.MinuteChange(item.value)
        }
    }

    private fun initSecondWheel() {
        if (!isSecondVisible) return
        mSecondAdapter.reRange(1, 60)
        mSecondWheel!!.adapter = mSecondAdapter
        mSecondWheel!!.setOnItemSelectedListener { index ->
            val item = mSecondAdapter.getItem(index)
            mValidator.SecondChange(item.value)
        }
    }

    //endregion
    //region 数据
    fun setSelected(calendar: Calendar) {
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val second = calendar[Calendar.SECOND]
        mValidator.setSelected(year, month, day, hour, minute, second)
    }

    //endregion
    //region 事件监听
    //endregion
    override fun onDateTimeValidated(
            year: ValidateResult,
            month: ValidateResult,
            dayOfMonth: ValidateResult,
            hourOfDay: ValidateResult,
            minute: ValidateResult,
            second: ValidateResult
    ) {
        if (isYearVisible) {
            mYearAdapter.reRange(year.rangeStart, year.rangeEnd)
            mYearWheel!!.currentItem = mYearAdapter.getItemIndex(year.current)
        }
        if (isMonthVisible) {
            mMonthAdapter.reRange(month.rangeStart, month.rangeEnd)
            mMonthWheel!!.currentItem = mMonthAdapter.getItemIndex(month.current)
        }
        if (isDayVisible) {
            mDayAdapter.reRange(dayOfMonth.rangeStart, dayOfMonth.rangeEnd)
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
        emitTimeChanged()
    }

    fun setRangDate(startDate: Calendar, endDate: Calendar) {
        mValidator.setRangDate(startDate, endDate)
    }

    //region callback
    private var mOnDatePickerSelectListener: OnDatePickerSelectListener? = null
    fun setDatePickerSelectListener(listener: OnDatePickerSelectListener?) {
        mOnDatePickerSelectListener = listener
    }

    private fun emitTimeChanged() {
        if (mOnDatePickerSelectListener != null) {
            mOnDatePickerSelectListener!!.onDatePickerSelect()
        }
    }

    //endregion
    val time: Date
        get() = mValidator.time

    companion object {
        //endregion
        //region 展示类型
        val DISPLAY_CONFIG = arrayOf(
                booleanArrayOf(true, true, true, false, false, false),
                booleanArrayOf(true, true, true, true, true, true),
                booleanArrayOf(true, true, false, false, false, false),
                booleanArrayOf(true, true, true, true, true, false),
                booleanArrayOf(false, false, false, true, true, false)
        )

        const val STYLE_DATE = 0
        const val STYLE_DATE_TIME = 1
        const val STYLE_YEAR_MONTH = 2
        const val STYLE_DATE_HOUR_MINUTE = 3
        const val STYLE_MOMENT = 4
    }

    init {
        mValidator.setValidatedListener(this)
    }
}