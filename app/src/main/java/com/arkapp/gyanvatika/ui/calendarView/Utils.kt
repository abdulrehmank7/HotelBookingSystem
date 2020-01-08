package com.arkapp.gyanvatika.ui.calendarView

import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.utils.*
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import com.arkapp.gyanvatika.utils.pojo.MonthBookingStatus
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
        val allDates = getAllDatesBetweenDays(
            event.startDateTimestamp.toLong(),
            event.endDateTimestamp.toLong())

        allDates.forEachIndexed { index, date ->
            val type =
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

fun getAllDatesBetweenDays(startTimestamp: Long, endTimestamp: Long): ArrayList<Date> {
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

fun searchEventInList(calendarDay: CalendarDay, eventList: ArrayList<GeneratedEvents>): GeneratedEvents? {
    val newDate = "${getTwoDigit(calendarDay.date.dayOfMonth)}-${getTwoDigit(calendarDay.date.monthValue)}-${calendarDay.date.year}"
    return eventList.find { it.eventDate == newDate }
}

fun getTwoDigit(month: Int): String {
    return if (month < 10)
        "0$month"
    else
        month.toString()
}

fun getMonthBookingStatus(generatedEvents: ArrayList<GeneratedEvents>, monthTxt: String): MonthBookingStatus {
    var totalBookedDays = 0
    var totalBookingAmount = 0.0
    var lastEventId = ""
    for (generatedEvent in generatedEvents) {
        if (generatedEvent.event != null) {
            if (getMonthTextFromDateTxt(generatedEvent.eventDate) == monthTxt) {
                totalBookedDays += 1
                if (generatedEvent.event!!.bookingAmount.isNotEmpty() &&
                    lastEventId != generatedEvent.event!!.id) {

                    lastEventId = generatedEvent.event!!.id
                    totalBookingAmount += generatedEvent.event!!.bookingAmount.toDouble()
                }
            }
        }
    }

    return MonthBookingStatus(totalBookedDays, totalBookingAmount)
}