package com.arkapp.gyanvatika.ui.newBooking

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.data.repository.SUCCESS
import com.arkapp.gyanvatika.ui.calendarView.generateMonthEventList
import com.arkapp.gyanvatika.ui.calendarView.getAllDatesBetweenDays
import com.arkapp.gyanvatika.utils.*
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NewBookingViewModel(private val repository: EventRepository,
                          private val session: PrefSession) : ViewModel() {

    var customerName: String = ""
    var customerPhone: String = ""
    var bookingAmount: String = ""
    var otherInfo: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var totalDays: String = ""
    var id: String = ""

    lateinit var startDateCalendar: Calendar
    lateinit var endDateCalendar: Calendar

    lateinit var listener: NewBookingListener

    fun onStartDateClicked(view: View) {
        listener.setStartDate()
    }

    fun onEndDateClicked(view: View) {
        listener.setEndDate()
    }

    fun onAddBookingClicked(view: View) {
        if (customerName.isEmpty()) {
            listener.customerNameEmpty(true)
            return
        } else {
            listener.customerNameEmpty(false)
            listener.showConfirmDialog()
        }

    }

    fun addEvent() {

        val event = Event(
            "",
            startDate,
            endDate,
            session.startEventTimestamp().toString(),
            session.endEventTimestamp().toString(),
            customerName,
            customerPhone,
            bookingAmount,
            otherInfo,
            formatDateFromCalendar(getCalendarForCurrentTime()),
            getCurrentTimestamp().toString()
        )

        val task = repository.addNewBooking(event)

        task.observeForever {
            if (it == SUCCESS)
                listener.onSuccess()
            else
                listener.onError(it)
        }
    }

    fun updateExistingEventData(event: Event): LiveData<String> {
        val updatedEvent = Event(
            event.id,
            startDate,
            endDate,
            session.startEventTimestamp().toString(),
            session.endEventTimestamp().toString(),
            customerName,
            customerPhone,
            bookingAmount,
            otherInfo,
            event.eventAddedDate,
            event.eventAddedDateTimestamp
        )
        return repository.updateBooking(updatedEvent)
    }

    fun getEventForCheckingExistingBooking() {
        CoroutineScope(Dispatchers.Main)
            .launch {
                val events = repository.getEvents(session.startEventTimestamp().getCalendarRef())
                val generatedList = generateMonthEventList(events)
                events.forEach {
                    "${printLog(it.customerName)} ${printLog(it.startDate)} ${printLog(it.endDate)}\n"
                }

                generatedList.forEach {
                    "${printLog(it.event!!.customerName)} ${printLog(it.eventDate)} ${printLog(it.eventDateType.toString())}\n"
                }
                listener.onEventsFetched(generatedList)
            }
    }

    fun checkIfBookingExists(fullMonthEvents: ArrayList<GeneratedEvents>): Boolean {

        val allDates = getAllDatesBetweenDays(
            session.startEventTimestamp(),
            session.endEventTimestamp())

        allDates.forEach { printLog("date" + formatDate(it)) }

        for (date in allDates) {

            val generatedEvent = fullMonthEvents.find { it.eventDate == formatDate(date) }
            if (generatedEvent != null && generatedEvent.event!!.id != id) {
                return true
            }
        }

        return false
    }
}
