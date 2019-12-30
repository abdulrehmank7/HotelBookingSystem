package com.arkapp.gyanvatika.utils


import android.util.Log



var LAST_CLICK_TIME: Long = 0

fun printLog(msg: String) {
    Log.e("Gyan Vatika logs", msg)
}

fun isNotDoubleClicked(minimumClickTimeInMilli: Long): Boolean {
    return if (getCurrentTimestamp() - LAST_CLICK_TIME < minimumClickTimeInMilli) {
        false
    } else {
        LAST_CLICK_TIME = getCurrentTimestamp()
        true
    }
}