package com.arkapp.gyanvatika.ui.home.calendarView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkapp.gyanvatika.data.repository.EventRepository

class CalendarViewModelFactory(private val repository: EventRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarViewModel(repository) as T
    }
}