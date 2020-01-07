package com.arkapp.gyanvatika.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkapp.gyanvatika.data.preferences.PrefSession

class SplashViewModelFactory(private val session: PrefSession) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(session) as T
    }
}