package me.foolishchow.android.datepicker

import android.view.View
import com.contrarywind.view.WheelView
import me.foolishchow.android.datepicker.adapters.DateWheelAdapter

/**
 * Description:
 * Author: foolishchow
 * Date: 2021/05/12 11:12 AM
 */
class LunarWheelTime
@JvmOverloads
constructor(
        private val mYearWheel: WheelView? = null,
        private val mMonthWheel: WheelView? = null,
        private val mDayWheel: WheelView? = null,
        private val mHourWheel: WheelView? = null,
        private val mMinuteWheel: WheelView? = null,
) {

    private val mValidator = LunarTimeValidator()

    private fun toggleVisible(wheelView: WheelView?, isVisible: Boolean) {
        if (wheelView != null) {
            wheelView.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private val isYearVisible: Boolean
        get() = mYearWheel != null && WheelTime.DISPLAY_CONFIG[mDateStyle][0]
    private val isMonthVisible: Boolean
        get() = mMonthWheel != null && WheelTime.DISPLAY_CONFIG[mDateStyle][1]
    private val isDayVisible: Boolean
        get() = mDayWheel != null && WheelTime.DISPLAY_CONFIG[mDateStyle][2]
    private val isHourVisible: Boolean
        get() = mHourWheel != null && WheelTime.DISPLAY_CONFIG[mDateStyle][3]
    private val isMinuteVisible: Boolean
        get() = mMinuteWheel != null && WheelTime.DISPLAY_CONFIG[mDateStyle][4]

    @WheelTime.DateStyle
    private var mDateStyle = WheelTime.STYLE_DATE
    fun setStyle(@WheelTime.DateStyle style: Int) {
        mDateStyle = style
        val displayConfig = WheelTime.DISPLAY_CONFIG[mDateStyle]
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
        //toggleVisible(mSecondWheel, displayConfig[5])
        //initSecondWheel()
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
        mYearWheel.setOnItemSelectedListener { index ->
            val item = mYearAdapter.getItem(index)
            mValidator.YearChange(item.value)
        }
    }

    private fun initMonthWheel() {
        if (!isMonthVisible) return
        mMonthAdapter.reRange(1, 12)
        mMonthWheel!!.adapter = mMonthAdapter
        // 添加"月"监听
        mMonthWheel.setOnItemSelectedListener { index ->
            val item = mMonthAdapter.getItem(index)
            mValidator.MonthChange(item.value)
        }
    }

    private fun initDayWheel() {
        if (!isDayVisible) return
        mDayAdapter.reRange(1, 31)
        mDayWheel!!.adapter = mDayAdapter
        mDayWheel.setOnItemSelectedListener { index ->
            val item = mDayAdapter.getItem(index)
            mValidator.DayChange(item.value)
        }
    }

    private fun initHourWheel() {
        if (!isHourVisible) return
        mHourAdapter.reRange(1, 24)
        mHourWheel!!.adapter = mHourAdapter
        mHourWheel.setOnItemSelectedListener { index ->
            val item = mHourAdapter.getItem(index)
            mValidator.HourChange(item.value)
        }
    }

    private fun initMinuteWheel() {
        if (!isMinuteVisible) return
        mMinuteAdapter.reRange(1, 60)
        mMinuteWheel!!.adapter = mMinuteAdapter
        mMinuteWheel.setOnItemSelectedListener { index ->
            val item = mMinuteAdapter.getItem(index)
            mValidator.MinuteChange(item.value)
        }
    }


}