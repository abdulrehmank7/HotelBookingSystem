package com.arkapp.gyanvatika.ui.newBooking

import android.annotation.SuppressLint
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.SUCCESS
import com.arkapp.gyanvatika.databinding.FragmentNewBookingBinding
import com.arkapp.gyanvatika.ui.home.HomeActivity
import com.arkapp.gyanvatika.utils.*
import com.arkapp.gyanvatika.utils.pojo.GeneratedEvents
import kotlinx.android.synthetic.main.fragment_new_booking.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import java.util.*


class NewBookingFragment : Fragment(), NewBookingListener, KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)

    override val kodein by kodein()

    private val session: PrefSession by instance()

    private val factory: NewBookingViewModelFactory by instance()

    private lateinit var safeArgs: NewBookingFragmentArgs

    private lateinit var confirmDialog: Dialog

    private lateinit var viewModel: NewBookingViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentNewBookingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_booking, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(NewBookingViewModel::class.java)

        viewModel.listener = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //initialize view model variables
        safeArgs = NewBookingFragmentArgs.fromBundle(arguments ?: Bundle())

        if (!safeArgs.startTimestamp.isNullOrEmpty()) {
            viewModel.startDateCalendar = safeArgs.startTimestamp!!.toLong().getCalendarRef()
            viewModel.endDateCalendar = safeArgs.startTimestamp!!.toLong().getCalendarRef()

            session.startEventTimestamp(safeArgs.startTimestamp!!.toLong())
            session.endEventTimestamp(safeArgs.startTimestamp!!.toLong())
        } else {
            viewModel.startDateCalendar = getCalendarForMidNight()
            viewModel.endDateCalendar = getCalendarForMidNight()

            session.startEventTimestamp(getCalendarForMidNight().timeInMillis)
            session.endEventTimestamp(getCalendarForMidNight().timeInMillis)
        }

        if (safeArgs.eventToUpdate != null) {
            val updateEvent = safeArgs.eventToUpdate!!
            viewModel.id = updateEvent.id

            viewModel.startDateCalendar = updateEvent.startDateTimestamp.toLong().getCalendarRef()
            viewModel.endDateCalendar = updateEvent.endDateTimestamp.toLong().getCalendarRef()

            session.startEventTimestamp(updateEvent.startDateTimestamp.toLong())
            session.endEventTimestamp(updateEvent.endDateTimestamp.toLong())

            viewModel.customerName = updateEvent.customerName
            viewModel.customerPhone = updateEvent.customerPhone
            viewModel.bookingAmount = updateEvent.bookingAmount
            viewModel.otherInfo = updateEvent.otherInfo
            viewModel.startDate = formatDateFromCalendar(updateEvent.startDateTimestamp.toLong().getCalendarRef())
            viewModel.endDate = formatDateFromCalendar(updateEvent.endDateTimestamp.toLong().getCalendarRef())
            viewModel.totalDays =
                getTotalDayBetweenDates(
                    updateEvent.startDateTimestamp.toLong().getCalendarRef(),
                    updateEvent.endDateTimestamp.toLong().getCalendarRef()).toString()

        } else {
            viewModel.startDate = formatDateFromCalendar(session.startEventTimestamp().getCalendarRef())
            viewModel.endDate = formatDateFromCalendar(session.endEventTimestamp().getCalendarRef())

            viewModel.totalDays =
                getTotalDayBetweenDates(
                    session.startEventTimestamp().getCalendarRef(),
                    session.endEventTimestamp().getCalendarRef()).toString()

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topActivity = (activity as HomeActivity)
        when {
            safeArgs.eventToUpdate != null -> {
                topActivity.hideBottomNavigation()
                addBookingBtn.text = getString(R.string.update_booking)
            }
            safeArgs.startTimestamp != null -> {
                topActivity.hideBottomNavigation()
            }
            else -> {
                (activity as HomeActivity).showBottomNavigation()
                addBookingBtn.text = getString(R.string.add_booking)
            }
        }
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

            session.startEventTimestamp(viewModel.startDateCalendar.timeInMillis)

            viewModel.startDate = formatDateFromCalendar(session.startEventTimestamp().getCalendarRef())

            viewModel.totalDays =
                getTotalDayBetweenDates(
                    session.startEventTimestamp().getCalendarRef(),
                    session.endEventTimestamp().getCalendarRef()).toString()

            if (session.startEventTimestamp() > session.endEventTimestamp()) {

                viewModel.endDateCalendar.set(Calendar.YEAR, year)
                viewModel.endDateCalendar.set(Calendar.MONTH, monthOfYear)
                viewModel.endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                session.endEventTimestamp(viewModel.endDateCalendar.timeInMillis)

                viewModel.endDate = formatDateFromCalendar(session.endEventTimestamp().getCalendarRef())

                viewModel.totalDays =
                    getTotalDayBetweenDates(
                        session.startEventTimestamp().getCalendarRef(),
                        session.endEventTimestamp().getCalendarRef()).toString()
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
            if (tempCheckCalendar.timeInMillis < session.startEventTimestamp()) {
                parentLay.showSnack(getString(R.string.time_check_error))
                return@OnDateSetListener
            }

            viewModel.endDateCalendar.set(Calendar.YEAR, year)
            viewModel.endDateCalendar.set(Calendar.MONTH, monthOfYear)
            viewModel.endDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            session.endEventTimestamp(viewModel.endDateCalendar.timeInMillis)

            viewModel.endDate = formatDateFromCalendar(session.endEventTimestamp().getCalendarRef())

            viewModel.totalDays =
                getTotalDayBetweenDates(
                    session.startEventTimestamp().getCalendarRef(),
                    session.endEventTimestamp().getCalendarRef()).toString()

            updateDateUI()

        }

        context!!.showDatePicker(endDateListener)
    }

    override fun onSuccess() {
        session.lastOpenedMonthTimestamp(0)
        confirmDialog.dismiss()
        parentLay.showSnack(getString(R.string.event_added_msg))
        view!!.findNavController().navigate(R.id.action_newBooking_to_calendarView)
    }

    override fun onError(msg: String) {
        parentLay.showSnackLong(msg)
    }

    override fun onEventsFetched(generatedList: ArrayList<GeneratedEvents>) {
        when {
            viewModel.checkIfBookingExists(generatedList) -> {
                confirmDialog.dismiss()
                parentLay.showSnackLong(getString(R.string.invalid_booking))
            }
            addBookingBtn.text == getString(R.string.add_booking) -> viewModel.addEvent()
            else -> {
                viewModel.updateExistingEventData(safeArgs.eventToUpdate!!)
                    .observe(this, androidx.lifecycle.Observer {
                        if (it == SUCCESS) {
                            session.lastOpenedMonthTimestamp(0)
                            parentLay.showSnack(getString(R.string.successfully_updated))
                            view!!.findNavController()
                                .navigate(R.id.action_newBooking_to_calendarView)
                        } else {
                            parentLay.showSnack(it)
                        }
                    })
            }
        }
    }

    override fun showConfirmDialog() {
        confirmDialog = DialogConfirmEventDetail(context!!, viewModel)
        if (addBookingBtn.text == getString(R.string.add_booking))
            confirmDialog.show()
        else {
            parentLay.showIndefiniteSnack(getString(R.string.updating_detail))!!
            viewModel.getEventForCheckingExistingBooking()
        }
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
