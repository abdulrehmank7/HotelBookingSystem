package com.arkapp.gyanvatika.ui.calendarView

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.databinding.FragmentCalendarViewBinding
import com.arkapp.gyanvatika.ui.home.HomeActivity
import com.arkapp.gyanvatika.utils.*
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.threeten.bp.YearMonth
import kotlin.math.roundToInt


class CalendarViewFragment : Fragment(), CalendarViewListener, KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)

    override val kodein by kodein()

    private val factory: CalendarViewModelFactory by instance()

    private val session: PrefSession by instance()

    private lateinit var calenderUI: CalendarUI

    private lateinit var viewModel: CalendarViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentCalendarViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calendar_view, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(CalendarViewModel::class.java)

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

        calenderUI = CalendarUI(calendarViewFragment, context!!)

        initCalendarUI()
    }

    private fun initCalendarUI() {
        progressBar.show()

        calenderUI.initialize(view!!, session)

        month.text = getMonthTextFromYearMonth(calenderUI.currentYearMonth.toString())

        calendarViewFragment.monthScrollListener = { date ->

            printLog("month scrolled ${date.yearMonth}")

            progressBar.show()

            viewModel.getEventsForCalendarMonth(getCalendarRef(1, date.month, date.year))
            calenderUI.currentYearMonth = date.yearMonth
            month.text = getMonthTextFromYearMonth(calenderUI.currentYearMonth.toString())

        }

        month.setOnClickListener {

            val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, _ ->
                calendarViewFragment.smoothScrollToMonth(YearMonth.of(year, monthOfYear + 1))
            }

            context!!.showDatePicker(dateListener)
        }

        rightArrowImg.setOnClickListener {
            calendarViewFragment.smoothScrollToMonth(calenderUI.currentYearMonth.plusMonths(1))
        }

        leftArrowImg.setOnClickListener {
            calendarViewFragment.smoothScrollToMonth(calenderUI.currentYearMonth.minusMonths(1))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onEventsFetched(generatedList: ArrayList<GeneratedEvents>) {
        try {
            calenderUI.setDatesOnCalender(generatedList, view!!)

            val monthBookingStatus = getMonthBookingStatus(generatedList, month.text.toString())

            bookedDaysTv.text = "${monthBookingStatus.days} Days"
            outOfDayTv.text = "Out of ${calenderUI.currentYearMonth.lengthOfMonth()} Days"

            if (session.showBookingAmount()) {
                if (monthBookingStatus.amount > 0) {
                    paymentGroup.show()
                    totalBookingAmountTv.text = "${getString(R.string.Rs)} ${monthBookingStatus.amount.roundToInt()}"
                } else
                    paymentGroup.hide()
            } else
                paymentGroup.hide()


            progressBar.hide()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
