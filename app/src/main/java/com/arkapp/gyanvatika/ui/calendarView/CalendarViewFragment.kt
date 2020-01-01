package com.arkapp.gyanvatika.ui.calendarView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.databinding.FragmentCalendarViewBinding
import com.arkapp.gyanvatika.ui.home.HomeActivity
import com.arkapp.gyanvatika.utils.*
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import kotlinx.android.synthetic.main.layout_progress_bar.*

class CalendarViewFragment : Fragment(), CalendarViewListener {

    private lateinit var calenderUI: CalendarUI

    private lateinit var viewModel: CalendarViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentCalendarViewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calendar_view, container, false)

        val factory = CalendarViewModelFactory(EventRepository())
        viewModel = ViewModelProviders
            .of(this, factory)
            .get(CalendarViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        viewModel.listener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).showBottomNavigation()
    }

    override fun onStart() {
        super.onStart()

        //getting the event for current month
        viewModel.initEvent(getCalendarForMidNight())

        calenderUI = CalendarUI(calendarViewFragment, context!!)

        initCalendarUI()
    }

    private fun initCalendarUI() {
        progressBar.show()

        calenderUI.initialize()

        month.text = getMonthText(calenderUI.currentMonth.toString())

        calendarViewFragment.monthScrollListener = { date ->

            progressBar.show()
            viewModel.initEvent(getCalendarRef(1, date.month, date.year))
            calenderUI.currentMonth = date.yearMonth
            month.text = getMonthText(calenderUI.currentMonth.toString())

        }

        rightArrowImg.setOnClickListener {
            calendarViewFragment.smoothScrollToMonth(calenderUI.currentMonth.plusMonths(1))
        }

        leftArrowImg.setOnClickListener {
            calendarViewFragment.smoothScrollToMonth(calenderUI.currentMonth.minusMonths(1))
        }
    }

    override fun onEventsFetched(generatedList: ArrayList<GeneratedEvents>) {

        try {
            calendarViewFragment.dayBinder = object : DayBinder<DayViewContainer> {

                override fun create(view: View) = DayViewContainer(view)

                override fun bind(container: DayViewContainer, day: CalendarDay) {

                    calenderUI.setDateText(container, day)
                    calenderUI.dimOtherMonth(container, day)
                    calenderUI.highlightCurrentDate(container, day)

                    val event = searchEventInList(day, generatedList)

                    container.dateTv.setOnClickListener {
                        val navController = view!!.findNavController()
                        val action = CalendarViewFragmentDirections
                            .actionCalendarViewFragmentToEventDetailFragment()
                        action.event = event
                        action.openedDateTimestamp = getCalendarRef(day.day, day.date.monthValue, day.date.year).timeInMillis.toString()

                        navController.navigate(action)
                    }

                    if (event != null) {
                        when (event.eventDateType) {
                            ONE_EVENT_DAY -> calenderUI.setOneDayEvent(container)
                            START_EVENT_DAY -> calenderUI.setEventStartDate(container)
                            END_EVENT_DAY -> calenderUI.setEventEndDate(container)
                            BETWEEN_EVENT_DAY -> calenderUI.setEventBetweenDate(container)
                        }
                    }
                }
            }
            progressBar.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
