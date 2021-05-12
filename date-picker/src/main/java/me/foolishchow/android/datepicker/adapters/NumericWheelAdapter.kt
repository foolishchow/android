package me.foolishchow.android.datepicker.adapters

import com.contrarywind.adapter.WheelAdapter

/**
 * Description:
 * Author: foolishchow
 * Date: 18/12/2020 4:30 PM
 * @param minValue the wheel min value
 * @param maxValue the wheel max value
 */
class NumericWheelAdapter(
        private val minValue: Int,
        private val maxValue: Int
) : WheelAdapter<Int> {
    override fun getItem(index: Int): Int {
        return if (index in 0 until itemsCount) {
            minValue + index
        } else 0
    }

    override fun indexOf(o: Int): Int {
        return try {
            o - minValue
        } catch (e: Throwable) {
            -1
        }
    }

    override fun getItemsCount(): Int {
        return maxValue - minValue + 1
    }
}