package me.foolishchow.android.datepicker.validator

import java.util.*

class MixedTimeValidator : IDateValidator {

    private val mLunarValidator = LunarTimeValidator()
    private val mSolarValidator = DateTimeValidator()

    override fun setRangeDate(startDate: Calendar, endDate: Calendar) {
        mLunarValidator.setRangeDate(startDate,endDate)
        mSolarValidator.setRangeDate(startDate,endDate)
    }

    override fun setSelected(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int, second: Int) {
        mLunarValidator.setSelected(year, month, dayOfMonth, hour, minute, second)
        mSolarValidator.setSelected(year, month, dayOfMonth, hour, minute, second)
    }

    private var mListener: ValidatedListener? =null
    override fun setValidatedListener(listener: ValidatedListener) {
        mListener = listener
    }

    init {
        mSolarValidator.setValidatedListener { year, month, dayOfMonth, hourOfDay, minute, second ->
            if(!mLunarMode) {
                mListener?.onDateTimeValidated(year, month, dayOfMonth, hourOfDay, minute, second)
            }
        }

        mLunarValidator.setValidatedListener { year, month, dayOfMonth, hourOfDay, minute, second ->
            if(mLunarMode) {
                mListener?.onDateTimeValidated(year, month, dayOfMonth, hourOfDay, minute, second)
            }
        }
    }


    val time:Date
        get(){
            return if(mLunarMode){
                mLunarValidator.time.toDate()
            }else{
                mSolarValidator.time
            }
        }
    private var mLunarMode = true
    fun setLunarMode(value: Boolean) {
        if (mLunarMode == value) return
        mLunarMode = value
        if(mLunarMode){
            mLunarValidator.Validate()
        }else{
            mSolarValidator.Validate()
        }
    }

    fun YearChange(year: Int) {
        if(mLunarMode){
            mLunarValidator.YearChange(year)
        } else {
            mSolarValidator.YearChange(year)
        }
    }

    fun MonthChange(month: Int) {
        if(mLunarMode){
            mLunarValidator.MonthChange(month)
        } else {
            mSolarValidator.MonthChange(month)
        }
    }

    fun DayChange(dayOfMonth: Int) {
        if(mLunarMode){
            mLunarValidator.DayChange(dayOfMonth)
        } else {
            mSolarValidator.DayChange(dayOfMonth)
        }
    }

    fun HourChange(hourOfDay: Int) {
        if(mLunarMode){
            mLunarValidator.HourChange(hourOfDay)
        } else {
            mSolarValidator.HourChange(hourOfDay)
        }
    }

    fun MinuteChange(minute: Int) {
        if(mLunarMode){
            mLunarValidator.MinuteChange(minute)
        } else {
            mSolarValidator.MinuteChange(minute)
        }
    }

    fun SecondChange(second: Int) {
        if(mLunarMode){
            mLunarValidator.SecondChange(second)
        } else {
            mSolarValidator.SecondChange(second)
        }
    }




}