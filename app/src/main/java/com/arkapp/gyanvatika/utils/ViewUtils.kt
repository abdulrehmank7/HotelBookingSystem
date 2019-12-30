package com.arkapp.gyanvatika.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import com.arkapp.gyanvatika.R
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun Activity.openActivity(toActivityClass: Class<*>, finishPreviousScreen: Boolean) {
    val intent = Intent(this, toActivityClass)
    startActivity(intent)

    if (finishPreviousScreen)
        finish()
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}


fun Group.show() {
    this.visibility = View.VISIBLE
}

fun Group.hide() {
    this.visibility = View.GONE
}

fun Context.showMessageDialog(title: String, message: String, buttonMsg: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton(buttonMsg, null)
    builder.show()
}

fun Activity.showSnack(msg: String?) {
    try {
        Snackbar.make(
            window.decorView.findViewById(R.id.content),
            msg!!,
            Snackbar.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String?.availableText(): String {
    return if (this.isNullOrEmpty())
        "unavailable"
    else this
}

fun Context.showDatePicker(listener: DatePickerDialog.OnDateSetListener) {
    DatePickerDialog(
        this,
        listener,
        getCalendarForMidNight()[Calendar.YEAR],
        getCalendarForMidNight()[Calendar.MONTH],
        getCalendarForMidNight()[Calendar.DAY_OF_MONTH])
        .show()
}
