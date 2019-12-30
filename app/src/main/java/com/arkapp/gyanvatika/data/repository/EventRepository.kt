package com.arkapp.gyanvatika.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkapp.gyanvatika.data.firestore.addNewEventDoc
import com.arkapp.gyanvatika.data.firestore.getMonthEvents
import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.utils.getEndTimeStampForQuery
import com.arkapp.gyanvatika.utils.getStartTimeStampForQuery
import java.util.*

const val SUCCESS = "success"

class EventRepository {

    fun addNewBooking(event: Event): LiveData<String> {

        val liveData = MutableLiveData<String>()

        addNewEventDoc(event).addOnCompleteListener { response ->
            if (response.isSuccessful)
                liveData.value = SUCCESS
            else
                liveData.value = response.exception?.localizedMessage
        }
        return liveData
    }

    suspend fun getEvents(selectedMonthCalendar: Calendar): List<Event> {
        return getMonthEvents(
            getStartTimeStampForQuery(selectedMonthCalendar),
            getEndTimeStampForQuery(selectedMonthCalendar))
    }
}