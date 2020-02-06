package com.arkapp.gyanvatika.ui.newBooking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.EventRepository

class NewBookingViewModelFactory(private val repository: EventRepository,
                                 private val session: PrefSession) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewBookingViewModel(repository,session) as T
    }
}