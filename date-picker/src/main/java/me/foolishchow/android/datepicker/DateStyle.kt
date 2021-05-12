package me.foolishchow.android.datepicker

import androidx.annotation.IntDef

object DateStyle {
    const val STYLE_DATE = 0
    const val STYLE_DATE_TIME = 1
    const val STYLE_YEAR_MONTH = 2
    const val STYLE_DATE_HOUR_MINUTE = 3
    const val STYLE_MOMENT = 4

    val CONFIG = arrayOf(
            booleanArrayOf(true, true, true, false, false, false),
            booleanArrayOf(true, true, true, true, true, true),
            booleanArrayOf(true, true, false, false, false, false),
            booleanArrayOf(true, true, true, true, true, false),
            booleanArrayOf(false, false, false, true, true, false)
    )

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(
            STYLE_DATE, STYLE_DATE_TIME, STYLE_YEAR_MONTH,
            STYLE_DATE_HOUR_MINUTE, STYLE_MOMENT
    )
    annotation class Style
}