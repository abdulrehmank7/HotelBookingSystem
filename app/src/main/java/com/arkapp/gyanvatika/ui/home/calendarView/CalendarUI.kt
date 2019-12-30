package com.arkapp.gyanvatika.ui.home.calendarView

import android.content.Context
import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.utils.DayViewContainer
import com.arkapp.gyanvatika.utils.getCurrentDayOfYear
import com.arkapp.gyanvatika.utils.invisible
import com.arkapp.gyanvatika.utils.show
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*

class CalendarUI(private val calendarView: CalendarView,
                 private val context: Context) {

    lateinit var currentMonth: YearMonth

    fun initialize() {

        currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(50)
        val lastMonth = currentMonth.plusMonths(120)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, day: CalendarDay) {

                setDateText(container, day)
                dimOtherMonth(container, day)
                highlightCurrentDate(container, day)

            }
        }

        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)
    }

    fun setDateText(container: DayViewContainer, day: CalendarDay) {
        container.dateTv.text = day.date.dayOfMonth.toString()
    }

    fun dimOtherMonth(container: DayViewContainer, day: CalendarDay) {
        if (day.owner != DayOwner.THIS_MONTH) {
            container.dateTv
                .setTextColor(ContextCompat.getColor(context, R.color.light_color))
        }
    }

    fun highlightCurrentDate(container: DayViewContainer, day: CalendarDay) {
        if (day.date.dayOfYear == getCurrentDayOfYear()) {

            container.dateTv
                .setTextColor(ContextCompat.getColor(context, R.color.colorAccent))

            container.dateTv.typeface = Typeface.DEFAULT_BOLD
        }
    }


    fun setEventStartDate(container: DayViewContainer) {
        container.roundBackground.show()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }

    fun setEventEndDate(container: DayViewContainer) {
        container.roundBackground.show()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    fun setEventBetweenDate(container: DayViewContainer) {
        container.roundBackground.invisible()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }


}