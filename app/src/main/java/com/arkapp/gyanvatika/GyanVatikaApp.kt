package com.arkapp.gyanvatika

import android.app.Application
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.ui.calendarView.CalendarViewModelFactory
import com.arkapp.gyanvatika.ui.eventDetails.EventDetailViewModelFactory
import com.arkapp.gyanvatika.ui.newBooking.NewBookingViewModelFactory
import com.arkapp.gyanvatika.ui.splashscreen.SplashViewModelFactory
import com.arkapp.gyanvatika.utils.setupCalligraphy
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class GyanVatikaApp : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        setupCalligraphy()
        AndroidThreeTen.init(this)
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GyanVatikaApp))

        bind() from singleton { EventRepository() }
        bind() from singleton { PrefSession(instance()) }
        bind() from provider { SplashViewModelFactory(instance()) }
        bind() from provider { CalendarViewModelFactory(instance(), instance()) }
        bind() from provider { NewBookingViewModelFactory(instance(), instance()) }
        bind() from provider { EventDetailViewModelFactory(instance()) }

    }

}