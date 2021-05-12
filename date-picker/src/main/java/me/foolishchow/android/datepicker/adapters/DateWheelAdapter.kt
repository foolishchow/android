package me.foolishchow.android.datepicker.adapters

import android.annotation.SuppressLint
import com.contrarywind.adapter.WheelAdapter
import me.foolishchow.android.datepicker.data.DateWheelVo
import java.util.*

class DateWheelAdapter
@JvmOverloads
constructor(private val formatNumber: Boolean = true)
    : WheelAdapter<DateWheelVo> {

    private var mStart = 0
    private var mEnd = 0

    @SuppressLint("DefaultLocale")
    fun reRange(start: Int, end: Int) {
        if (mStart == start && mEnd == end) return
        mStart = start
        mEnd = end
        val list: MutableList<DateWheelVo> = ArrayList()
        for (current in start..end) {
            if (formatNumber) {
                list.add(DateWheelVo(String.format("%02d", current), current))
            } else {
                list.add(DateWheelVo(String.format("%d", current), current))
            }
        }
        mList.clear()
        mList.addAll(list)
    }

    fun getItemIndex(value: Int): Int {
        return value - mStart
    }

    private val mList = mutableListOf<DateWheelVo>()
    override fun getItemsCount(): Int {
        return mList.size
    }

    override fun getItem(index: Int): DateWheelVo {
        return if (index < 0 || index >= itemsCount) {
            mDefault
        } else mList[index]
    }

    override fun indexOf(o: DateWheelVo): Int {
        return mList.indexOf(o)
    }

    companion object {
        var mDefault = DateWheelVo("01", 0)
    }
}