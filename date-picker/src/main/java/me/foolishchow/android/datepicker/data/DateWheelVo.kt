package me.foolishchow.android.datepicker.data

import com.contrarywind.interfaces.IPickerViewData

open class DateWheelVo(
        @JvmField
        open val label: String,
        @JvmField
        open val value: Int
) : IPickerViewData {
    override fun getPickerViewText(): String {
        return label
    }

    override fun toString(): String {
        return "DateWheelVo{label='$label', value=$value}"
    }
}