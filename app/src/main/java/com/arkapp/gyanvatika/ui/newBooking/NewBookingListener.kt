package com.arkapp.gyanvatika.ui.newBooking

interface NewBookingListener {
    fun setStartDate()
    fun setEndDate()
    fun onSuccess()
    fun onError(msg: String)
    fun customerNameEmpty(isEmpty: Boolean)
    fun showConfirmDialog()
}