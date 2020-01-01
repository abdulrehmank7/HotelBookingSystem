package com.arkapp.gyanvatika.ui.eventDetails

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.data.repository.EventRepository

class EventDetailViewModel(private val repository: EventRepository) : ViewModel() {

    var customerName: String = ""
    var customerPhone: String = ""
    var bookingAmount: String = ""
    var otherInfo: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var totalDays: String = ""
    var currentDate: String = ""

    lateinit var listener: EventDetailListener

    fun mainFabClicked(view: View){
        listener.onMainFabClicked()
    }

    fun deleteFabClicked(view: View){
        listener.onDeleteClicked()
    }

    fun editFabClicked(view: View){
        listener.onEditClicked()
    }

    fun deleteEvent(event: Event): LiveData<String> {
     return repository.deleteBooking(event)
    }
}
