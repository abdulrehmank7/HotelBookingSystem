package com.arkapp.gyanvatika.ui.calendarView

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel(private val repository: EventRepository) : ViewModel() {

    private lateinit var job: Job

    lateinit var listener: CalendarViewListener


    fun initEvent(calendarDate: Calendar) {

        job = CoroutineScope(Dispatchers.Main)
            .launch {
                val events = repository.getEvents(calendarDate)
                val generatedList = generateMonthEventList(events)
                listener.onEventsFetched(generatedList)
            }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        job.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        // code here
    }
}
