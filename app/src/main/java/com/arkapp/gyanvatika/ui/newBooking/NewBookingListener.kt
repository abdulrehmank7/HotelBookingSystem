package com.arkapp.gyanvatika.ui.newBooking

import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents

interface NewBookingListener {
    fun setStartDate()
    fun setEndDate()
    fun onSuccess()
    fun onError(msg: String)
    fun customerNameEmpty(isEmpty: Boolean)
    fun showConfirmDialog()
    fun onEventsFetched(generatedList: ArrayList<GeneratedEvents>)
}