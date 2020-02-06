package com.arkapp.gyanvatika.ui.calendarView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.EventRepository

class CalendarViewModelFactory(private val repository: EventRepository,
                               private val session: PrefSession) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarViewModel(repository,session) as T
    }
}