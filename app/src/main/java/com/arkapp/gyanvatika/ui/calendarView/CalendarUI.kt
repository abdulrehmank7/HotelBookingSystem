package com.arkapp.gyanvatika.ui.calendarView

import android.content.Context
import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.utils.*
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList

class CalendarUI(private val calendarView: CalendarView,
                 private val context: Context) {

    lateinit var currentMonth: YearMonth

    fun initialize(fragmentView: View, session: PrefSession) {

        currentMonth =
            if (session.lastOpenedMonthTimestamp() == 0.toLong())
                YearMonth.now()
            else {
                val lastMonthCalendarRef = session.lastOpenedMonthTimestamp().getCalendarRef()
                YearMonth.of(lastMonthCalendarRef.get(Calendar.YEAR), lastMonthCalendarRef.get(Calendar.MONTH)+1)
            }
        printLog("init year month $currentMonth")
        val firstMonth = currentMonth.minusMonths(50)
        val lastMonth = currentMonth.plusMonths(120)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        setDatesOnCalender(ArrayList(), fragmentView)

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

    fun setOneDayEvent(container: DayViewContainer) {
        container.roundBackground.show()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }

    fun setEventStartDate(container: DayViewContainer) {
        container.roundBackground.show()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }

    fun setEventEndDate(container: DayViewContainer) {
        container.roundBackground.show()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
    }

    fun setEventBetweenDate(container: DayViewContainer) {
        container.roundBackground.invisible()
        container.endBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
        container.startBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
    }


    fun setDatesOnCalender(generatedList: ArrayList<GeneratedEvents>, fragmentView: View) {
        try {
            calendarView.dayBinder = object : DayBinder<DayViewContainer> {

                override fun create(view: View) = DayViewContainer(view)

                override fun bind(container: DayViewContainer, day: CalendarDay) {

                    setDateText(container, day)
                    dimOtherMonth(container, day)
                    highlightCurrentDate(container, day)

                    val event = searchEventInList(day, generatedList)

                    container.dateTv.setOnClickListener {
                        val navController = fragmentView.findNavController()
                        val action = CalendarViewFragmentDirections
                            .actionCalendarViewFragmentToEventDetailFragment()
                        action.event = event
                        action.openedDateTimestamp = getCalendarRef(day.day, day.date.monthValue, day.date.year).timeInMillis.toString()

                        navController.navigate(action)
                    }

                    if (event != null) {
                        when (event.eventDateType) {
                            ONE_EVENT_DAY -> setOneDayEvent(container)
                            START_EVENT_DAY -> setEventStartDate(container)
                            END_EVENT_DAY -> setEventEndDate(container)
                            BETWEEN_EVENT_DAY -> setEventBetweenDate(container)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}