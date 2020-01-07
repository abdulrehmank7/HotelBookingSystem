package com.arkapp.gyanvatika.ui.splashscreen

import android.view.View
import androidx.lifecycle.ViewModel
import com.arkapp.gyanvatika.data.preferences.PrefSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val session: PrefSession) : ViewModel() {
    lateinit var dataListener: SplashDataListener


    fun isUserLoggedIn() {
        CoroutineScope(Dispatchers.Main)
            .launch {
                if (session.isLoggedIn())
                    openApp()
                else
                    dataListener.showLogin()
            }
    }

    fun openApp() {
        CoroutineScope(Dispatchers.Main)
            .launch {
                session.isLoggedIn(true)
                delay(1500)
                dataListener.openApp()
            }
    }

    fun onLoginClicked(view: View) {
        dataListener.startLogin()
    }

    override fun onCleared() {
        super.onCleared()
        session.lastOpenedMonthTimestamp(0)
    }
}
