package com.arkapp.gyanvatika

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class GyanVatikaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}