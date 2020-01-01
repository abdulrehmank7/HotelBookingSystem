package com.arkapp.gyanvatika.ui.calendarView

import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.utils.formatDate
import com.arkapp.gyanvatika.utils.getCalendarForMidNight
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import com.kizitonwose.calendarview.model.CalendarDay
import java.util.*
import kotlin.collections.ArrayList

const val BUFFER_DAY = 7
const val START_EVENT_DAY = 1
const val END_EVENT_DAY = 2
const val BETWEEN_EVENT_DAY = 3
const val ONE_EVENT_DAY = 4

fun generateMonthEventList(eventList: List<Event>): ArrayList<GeneratedEvents> {
    val generatedList = ArrayList<GeneratedEvents>()
    for (event in eventList) {
        val allDates = getAllBetweenDays(
            event.startDateTimestamp.toLong(),
            event.endDateTimestamp.toLong())

        allDates.forEachIndexed { index, date ->
            val type=
                if (allDates.size == 1)
                    ONE_EVENT_DAY
                else when (index) {
                    0 -> START_EVENT_DAY
                    allDates.size - 1 -> END_EVENT_DAY
                    else -> BETWEEN_EVENT_DAY
                }
            generatedList.add(GeneratedEvents(event, formatDate(date), type))
        }

    }
    return generatedList
}

fun getAllBetweenDays(startTimestamp: Long, endTimestamp: Long): ArrayList<Date> {
    val dates: ArrayList<Date> = ArrayList()

    val startCalendar = getCalendarForMidNight()
    startCalendar.timeInMillis = startTimestamp

    val endCalendar = getCalendarForMidNight()
    endCalendar.timeInMillis = endTimestamp

    while (!startCalendar.after(endCalendar)) {
        dates.add(startCalendar.time)
        startCalendar.add(Calendar.DATE, 1)
    }

    return dates
}

fun searchEventInList(date: CalendarDay, eventList: ArrayList<GeneratedEvents>): GeneratedEvents? {
    val newDate = "${getTwoDigit(date.date.dayOfMonth)}-${getTwoDigit(date.date.monthValue)}-${date.date.year}"
    return eventList.find { it.eventDate == newDate }
}

fun getTwoDigit(month: Int): String {
    return if (month < 10)
        "0$month"
    else
        month.toString()
}