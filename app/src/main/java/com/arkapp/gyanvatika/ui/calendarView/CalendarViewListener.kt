package com.arkapp.gyanvatika.ui.calendarView

import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents

interface CalendarViewListener {

    fun onEventsFetched(generatedList: ArrayList<GeneratedEvents>)
}