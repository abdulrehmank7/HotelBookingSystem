package com.arkapp.gyanvatika.ui.home.calendarView

import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents

interface CalendarViewListener {

    fun onEventsFetched(generatedList: ArrayList<GeneratedEvents>)
}