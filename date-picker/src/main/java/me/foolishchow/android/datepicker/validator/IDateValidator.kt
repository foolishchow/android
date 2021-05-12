package me.foolishchow.android.datepicker.validator

import java.util.*

interface IDateValidator {
    fun setRangeDate(startDate:Calendar,endDate:Calendar)
    fun setSelected(year:Int,month:Int,dayOfMonth:Int,hour:Int,minute:Int,second:Int)
    fun setValidatedListener(listener: ValidatedListener)
}