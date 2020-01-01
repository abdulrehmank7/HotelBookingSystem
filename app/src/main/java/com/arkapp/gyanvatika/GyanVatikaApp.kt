package com.arkapp.gyanvatika

import android.app.Application
import com.arkapp.gyanvatika.utils.setupCalligraphy
import com.jakewharton.threetenabp.AndroidThreeTen

class GyanVatikaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupCalligraphy()
        AndroidThreeTen.init(this)
    }
}