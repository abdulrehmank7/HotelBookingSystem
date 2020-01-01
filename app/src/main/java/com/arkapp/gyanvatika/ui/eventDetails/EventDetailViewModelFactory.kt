package com.arkapp.gyanvatika.ui.eventDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkapp.gyanvatika.data.repository.EventRepository

class EventDetailViewModelFactory(private val repository: EventRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventDetailViewModel(repository) as T
    }
}