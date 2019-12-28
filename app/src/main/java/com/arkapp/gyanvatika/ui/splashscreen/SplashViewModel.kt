package com.arkapp.gyanvatika.ui.splashscreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    var dataListener: SplashDataListener? = null

    fun fetchBookings() {
        CoroutineScope(Dispatchers.Main)
            .launch {
                delay(100)
                dataListener!!.onDataFetched()
            }
    }
}
