package com.arkapp.gyanvatika.utils

import android.annotation.SuppressLint
import com.arkapp.gyanvatika.ui.calendarView.BUFFER_DAY
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

val MONTH_NAME = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

fun getCalendarForCurrentTime(): Calendar = Calendar.getInstance()

fun getCurrentTimestamp() = System.currentTimeMillis()

fun formatDateFromCalendar(calendar: Calendar): String {

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val newDate = if (day < 10) "0$day" else "$day"

    return "$newDate-${MONTH_NAME[month]}-$year"
}

fun getTotalDayBetweenDates(startCalendar: Calendar, endCalendar: Calendar): Int {
    val msDiff: Long = endCalendar.timeInMillis - startCalendar.timeInMillis
    return ((msDiff / 1000 / 60 / 60 / 24) + 1).toInt().absoluteValue
}

fun getCalendarForMidNight(): Calendar {
    val mCalendar: Calendar = Calendar.getInstance()
    mCalendar.set(Calendar.HOUR_OF_DAY, 1)
    mCalendar.set(Calendar.MINUTE, 0)
    return mCalendar
}

fun getStartTimeStampForDBQuery(calendar: Calendar): Long {
    calendar.add(Calendar.MONTH, -1)
    val lastSeventhDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - BUFFER_DAY
    calendar.set(Calendar.DAY_OF_MONTH, lastSeventhDay)
    return calendar.timeInMillis
}

fun getEndTimeStampForDBQuery(calendar: Calendar): Long {
    calendar.add(Calendar.MONTH, 2)
    calendar.set(Calendar.DAY_OF_MONTH, BUFFER_DAY)
    return calendar.timeInMillis
}

fun getMonthTextFromYearMonth(yearMonth: String): String {
    val month = MONTH_NAME[yearMonth.substring(5).toInt() - 1]
    return "$month - ${yearMonth.substring(0, 4)}"
}

fun getMonthTextFromDateTxt(date: String): String {
    val month = MONTH_NAME[date.substring(3,5).toInt() - 1]
    return "$month - ${date.substring( 6)}"
}

fun getCurrentDayOfYear() = getCalendarForCurrentTime().get(Calendar.DAY_OF_YEAR)

@SuppressLint("SimpleDateFormat")
fun formatDate(date: Date): String {
    return SimpleDateFormat("dd-MM-yyyy").format(date)
}

@SuppressLint("SimpleDateFormat")
fun formatDate(calendar: Calendar): String {
    return SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
}

fun getCalendarRef(date: Int, month: Int, year: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)
    calendar.set(Calendar.DAY_OF_MONTH, date)
    calendar.set(Calendar.HOUR_OF_DAY, 1)
    calendar.set(Calendar.MINUTE, 0)
    return calendar
}


fun Long.getCalendarRef(): Calendar {
    val calendar = getCalendarForMidNight()
    calendar.timeInMillis = this
    return calendar
}