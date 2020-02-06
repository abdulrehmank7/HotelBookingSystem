package com.arkapp.gyanvatika.ui.calendarView

import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.utils.getCalendarRef
import com.arkapp.gyanvatika.utils.printLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(private val repository: EventRepository,
                        private val session: PrefSession) : ViewModel() {


    lateinit var listener: CalendarViewListener


    fun getEventsForCalendarMonth(calendarDate: Calendar) {
        printLog("event month timestamp...${calendarDate.timeInMillis}")
        printLog("last timestamp...${session.lastOpenedMonthTimestamp()}")

        if (session.lastOpenedMonthTimestamp().toInt() != 0 &&
            session.lastOpenedMonthTimestamp().getCalendarRef().get(Calendar.DAY_OF_YEAR) ==
            calendarDate.get(Calendar.DAY_OF_YEAR)) {

            listener.onEventsFetched(session.lastOpenedMonthEvent())

        } else {
            CoroutineScope(Dispatchers.Main)
                .launch {
                    session.lastOpenedMonthTimestamp(calendarDate.timeInMillis)
                    printLog("setting last timestamp...${session.lastOpenedMonthTimestamp()}")

                    val events = repository.getEvents(calendarDate)
                    val generatedList = generateMonthEventList(events)

                    session.lastOpenedMonthEvent(generatedList)


                    listener.onEventsFetched(generatedList)
                }
        }
    }
}
