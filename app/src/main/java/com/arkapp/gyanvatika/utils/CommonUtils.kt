package com.arkapp.gyanvatika.utils


import android.app.Activity
import android.util.Log
import com.arkapp.gyanvatika.R
import com.firebase.ui.auth.AuthUI


var LAST_CLICK_TIME: Long = 0
const val RC_SIGN_IN = 310

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

fun openLoginScreen(activity: Activity) {
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )


    activity.startActivityForResult(
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.LoginTheme)
            .setIsSmartLockEnabled(false)
            .build(),
        RC_SIGN_IN)
}