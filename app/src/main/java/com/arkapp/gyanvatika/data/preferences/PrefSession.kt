package com.arkapp.gyanvatika.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PrefSession(val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()
    private val gson = Gson()

    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }


    private fun String.getLong(): Long {
        return pref.getLong(this, 0)
    }

    private fun String.getInt(): Int {
        return pref.getInt(this, 0)
    }

    private fun String.getString(): String {
        return pref.getString(this, "")!!
    }

    private fun String.getBoolean(): Boolean {
        return pref.getBoolean(this, false)
    }

    /*********************************************/

    fun clearData() {
        editor.clear()
        editor.commit()
    }

    fun isLoggedIn(isLoggedIn: Boolean) {
        PREF_LOGGED_IN.put(isLoggedIn)
    }

    fun isLoggedIn() = PREF_LOGGED_IN.getBoolean()

    fun startEventTimestamp(timestamp: Long) {
        PREF_START_TIME_STAMP.put(timestamp)
    }

    fun startEventTimestamp() = PREF_START_TIME_STAMP.getLong()

    fun endEventTimestamp(timestamp: Long) {
        PREF_END_TIME_STAMP.put(timestamp)
    }

    fun endEventTimestamp() = PREF_END_TIME_STAMP.getLong()

    fun lastOpenedMonthTimestamp(date: Long) {
        PREF_LAST_OPENED_MONTH.put(date)
    }

    fun lastOpenedMonthTimestamp() = PREF_LAST_OPENED_MONTH.getLong()

    fun lastOpenedMonthEvent(generatedEvents: ArrayList<GeneratedEvents>) {
        PREF_LAST_OPENED_MONTH_EVENTS.put(gson.toJson(generatedEvents))
    }

    fun lastOpenedMonthEvent(): ArrayList<GeneratedEvents> {
        val type = object : TypeToken<ArrayList<GeneratedEvents?>?>() {}.type
        return gson.fromJson<ArrayList<GeneratedEvents>>(
            PREF_LAST_OPENED_MONTH_EVENTS.getString(),
            type) ?: ArrayList()
    }

    fun showBookingAmount(toShow: Boolean) {
        PREF_SETTING_SHOW_BOOKING_AMOUNT.put(toShow)
    }

    fun showBookingAmount() = PREF_SETTING_SHOW_BOOKING_AMOUNT.getBoolean()

}