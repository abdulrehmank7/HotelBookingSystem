package com.arkapp.gyanvatika.utils

import android.app.Activity
import android.content.Intent

fun Activity.openActivity(toActivityClass: Class<*>, finishPreviousScreen: Boolean) {
    val intent = Intent(this, toActivityClass)
    startActivity(intent)

    if (finishPreviousScreen)
        finish()
}