package com.arkapp.gyanvatika.ui.newBooking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkapp.gyanvatika.data.repository.EventRepository

class NewBookingViewModelFactory(private val repository: EventRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewBookingViewModel(repository) as T
    }
}