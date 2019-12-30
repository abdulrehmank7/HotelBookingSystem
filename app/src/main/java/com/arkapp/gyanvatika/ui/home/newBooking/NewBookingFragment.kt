package com.arkapp.gyanvatika.ui.home.newBooking

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.repository.EventRepository
import com.arkapp.gyanvatika.databinding.FragmentNewBookingBinding
import com.arkapp.gyanvatika.utils.*
import kotlinx.android.synthetic.main.fragment_new_booking.*
import java.util.*


class NewBookingFragment : Fragment(), NewBookingListener {

    private lateinit var confirmDialog: Dialog

    private lateinit var viewModel: NewBookingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentNewBookingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_booking, container, false)

        val factory = NewBookingViewModelFactory(EventRepository())
        viewModel = ViewModelProviders.of(this, factory).get(NewBookingViewModel::class.java)

        viewModel.listener = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //initialize view model variables
        viewModel.startDateCalendar = getCalendarForMidNight()
        viewModel.endDateCalendar = getCalendarForMidNight()

        viewModel.startDate = formatDateFromCalendar(viewModel.startDateCalendar)
        viewModel.endDate = formatDateFromCalendar(viewModel.endDateCalendar)

        viewModel.totalDays =
            getTotalDayBetweenDates(viewModel.startDateCalendar, viewModel.endDateCalendar).toString()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //updating the date on layout for the first time
        updateDateUI()
    }

    @SuppressLint("SetTextI18n")
    override fun setStartDate() {
        val startDateListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

            //update the view model start date
            viewModel.startDateCalendar.set(Calendar.YEAR, year)
            viewModel.startDateCalendar.set(Calendar.MONTH, monthOfYear)
            viewModel.startDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            viewModel.startDate = formatDateFromCalendar(viewModel.startDateCalendar)

            viewModel.totalDays =
                getTotalDayBetweenDates(viewModel.startDateCalendar, viewModel.endDateCalendar).toString()

            if (viewModel.startDateCalendar.timeInMillis > viewModel.endDateCalendar.timeInMillis) {

                viewModel.endDateCalendar.set(Calendar.YEAR, year)
                viewModel.endDateCalendar.set(Calendar.MONTH, monthOfYear)
                viewModel.endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewModel.endDate = formatDateFromCalendar(viewModel.endDateCalendar)

                viewModel.totalDays =
                    getTotalDayBetweenDates(viewModel.startDateCalendar, viewModel.endDateCalendar).toString()
            }

            updateDateUI()

        }
        context!!.showDatePicker(startDateListener)
    }

    @SuppressLint("SetTextI18n")
    override fun setEndDate() {
        val endDateListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

            val tempCheckCalendar: Calendar = getCalendarForMidNight()
            tempCheckCalendar.set(Calendar.YEAR, year)
            tempCheckCalendar.set(Calendar.MONTH, monthOfYear)
            tempCheckCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            //update the view model end date
            if (tempCheckCalendar.timeInMillis < viewModel.startDateCalendar.timeInMillis) {
                context!!.toast(getString(R.string.time_check_error))
                return@OnDateSetListener
            }

            viewModel.endDateCalendar.set(Calendar.YEAR, year)
            viewModel.endDateCalendar.set(Calendar.MONTH, monthOfYear)
            viewModel.endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            viewModel.endDate = formatDateFromCalendar(viewModel.endDateCalendar)

            viewModel.totalDays =
                getTotalDayBetweenDates(viewModel.startDateCalendar, viewModel.endDateCalendar).toString()

            updateDateUI()

        }

        context!!.showDatePicker(endDateListener)
    }

    override fun onSuccess() {
        confirmDialog.dismiss()
        context!!.toast(getString(R.string.event_added_msg))
    }

    override fun showConfirmDialog() {
        confirmDialog = DialogConfirmEventDetail(context!!, viewModel)
            .also {
                it.show()
            }
    }

    override fun onError(msg: String) {
        context!!.toast(msg)
    }

    override fun customerNameEmpty(isEmpty: Boolean) {
        if (isEmpty)
            customerName.error = getString(R.string.customer_name_error)
        else
            customerName.error = null
    }

    @SuppressLint("SetTextI18n")
    private fun updateDateUI() {
        //update the text view in layout when date is changed
        fromDateTv.text = viewModel.startDate
        toDateTv.text = viewModel.endDate
        totalDaysTv.text = "${getString(R.string.total_days)} ${viewModel.totalDays} ${getString(R.string.days)}"
    }
}
