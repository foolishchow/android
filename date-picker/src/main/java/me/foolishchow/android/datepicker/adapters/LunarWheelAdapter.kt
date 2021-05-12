package me.foolishchow.android.datepicker.adapters

import com.contrarywind.adapter.WheelAdapter
import me.foolishchow.android.datepicker.data.DateWheelVo

/**
 * Description:
 * Author: foolishchow
 * Date: 2021/05/12 2:55 PM
 */
class LunarWheelAdapter : WheelAdapter<DateWheelVo> {
    private val mList = mutableListOf<DateWheelVo>()
    override fun getItemsCount(): Int {
        return mList.size
    }

    private var start: Int = 0;
    private var end: Int = 0;
    fun rangeChanged(from: Int, to: Int): Boolean {
        if (start != from || to != end) {
            start = from
            end = to
            return true
        }
        return false
    }

    fun getItemIndex(value: Int): Int {
        return value - start
    }

    fun reRange(list: List<DateWheelVo>) {
        mList.clear()
        mList.addAll(list)
    }

    override fun getItem(index: Int): DateWheelVo {
        return if (index < 0 || index >= itemsCount) {
            mDefault
        } else mList[index]
    }

    override fun indexOf(o: DateWheelVo?): Int {
        return mList.indexOf(o)
    }

    companion object {
        var mDefault = DateWheelVo("01", 0)
    }

}