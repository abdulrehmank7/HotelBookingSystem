package com.arkapp.gyanvatika.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SplashViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel() as T
    }
}