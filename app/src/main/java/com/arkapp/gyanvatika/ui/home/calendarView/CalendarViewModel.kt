package com.arkapp.gyanvatika.ui.home.calendarView

import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.utils.getCalendarForMidNight
import com.arkapp.gyanvatika.utils.printLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(private val repository: EventRepository) : ViewModel() {

    lateinit var listener: CalendarViewListener


    fun initEvent(calendarDate: Calendar) {

        CoroutineScope(Dispatchers.Main)
            .launch {

                val events = repository.getEvents(calendarDate)
                val generatedList = generateMonthEventList(events)
                listener.onEventsFetched(generatedList)


                events.forEach {
                    "${printLog(it.customerName)} ${printLog(it.customerPhone)} ${printLog(it.startDate)} ${printLog(it.endDate)}\n"
                }

                generatedList.forEach {
                    "${printLog(it.event!!.customerName)} ${printLog(it.eventDate)} ${printLog(it.eventDateType.toString())}\n"
                }

            }

    }
}
