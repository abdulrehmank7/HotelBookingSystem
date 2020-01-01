package com.arkapp.gyanvatika.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day_layout.view.*

class DayViewContainer(view: View) : ViewContainer(view) {
    val parent: ConstraintLayout = view.parentLay
    val dateTv: TextView = view.calendarDayText
    val roundBackground: ImageView = view.roundBackground
    val startBorder: ImageView = view.startBorder
    val endBorder: ImageView = view.endBorder
}