package com.arkapp.gyanvatika.ui.newBooking

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.data.repository.SUCCESS
import com.arkapp.gyanvatika.utils.formatDateFromCalendar
import com.arkapp.gyanvatika.utils.getCalendarForCurrentTime
import com.arkapp.gyanvatika.utils.getCurrentTimestamp
import java.util.*

class NewBookingViewModel(private val repositiory: EventRepository) : ViewModel() {

    var customerName: String = ""
    var customerPhone: String = ""
    var bookingAmount: String = ""
    var otherInfo: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var totalDays: String = ""

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
            startDateCalendar.timeInMillis.toString(),
            endDateCalendar.timeInMillis.toString(),
            customerName,
            customerPhone,
            bookingAmount,
            otherInfo,
            formatDateFromCalendar(getCalendarForCurrentTime()),
            getCurrentTimestamp().toString()
        )

        val task = repositiory.addNewBooking(event)

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
            startDateCalendar.timeInMillis.toString(),
            endDateCalendar.timeInMillis.toString(),
            customerName,
            customerPhone,
            bookingAmount,
            otherInfo,
            event.eventAddedDate,
            event.eventAddedDateTimestamp
        )
        return repositiory.updateBooking(updatedEvent)
    }
}
