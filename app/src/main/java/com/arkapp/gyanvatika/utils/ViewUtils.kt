package com.arkapp.gyanvatika.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import com.arkapp.gyanvatika.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
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

fun View.showSnack(msg: String?) {
    try {
        Snackbar.make(
            this,
            msg!!,
            Snackbar.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.showSnackLong(msg: String?) {
    try {
        Snackbar.make(
            this,
            msg!!,
            Snackbar.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.showIndefiniteSnack(msg: String?): Snackbar? {
    try {
        return Snackbar.make(
            this,
            msg!!,
            Snackbar.LENGTH_INDEFINITE).also { it.show() }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
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

fun Context.showDialog(
    title: String,
    message: String,
    negativeMsg: String): MaterialAlertDialogBuilder {

    val dialog = MaterialAlertDialogBuilder(this)
    dialog.setTitle(title)
        .setMessage(message)
        .setNegativeButton(negativeMsg) { dialogInterface, _ -> dialogInterface.dismiss() }

    return dialog
}

fun setupCalligraphy() {
    ViewPump.init(ViewPump.builder()
        .addInterceptor(CalligraphyInterceptor(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()))
        .build())
}

@SuppressLint("DefaultLocale")
fun String?.capitalizeFirstWords(): String {
    return if (this.isNullOrBlank())
        ""
    else {
        var output = ""
        val words = this.trim().split(" ").toMutableList()
        for (word in words)
            output += word.capitalize() + " "
        output.trim()
    }
}