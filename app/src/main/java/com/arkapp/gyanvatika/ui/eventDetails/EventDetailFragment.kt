package com.arkapp.gyanvatika.ui.eventDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.data.repository.SUCCESS
import com.arkapp.gyanvatika.databinding.FragmentEventDetailBinding
import com.arkapp.gyanvatika.ui.home.HomeActivity
import com.arkapp.gyanvatika.utils.*
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class EventDetailFragment : Fragment(), EventDetailListener, KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)

    override val kodein by kodein()

    private val session: PrefSession by instance()

    private val factory: EventDetailViewModelFactory by instance()

    private lateinit var event: Event

    private lateinit var safeArgs: EventDetailFragmentArgs

    private lateinit var viewModel: EventDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentEventDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false)

        viewModel = ViewModelProviders.of(this, factory).get(EventDetailViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.listener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).hideBottomNavigation()

        safeArgs = EventDetailFragmentArgs.fromBundle(arguments!!)

        viewModel.currentDate = formatDateFromCalendar(safeArgs.openedDateTimestamp!!.toLong().getCalendarRef())

        if (safeArgs.event != null) {
            event = safeArgs.event?.event!!
            viewModel.customerName = event.customerName
            viewModel.customerPhone = event.customerPhone.availableText()
            viewModel.bookingAmount = "${getString(R.string.Rs)} ${event.bookingAmount}".availableText()
            viewModel.otherInfo = event.otherInfo.availableText()
            viewModel.startDate = event.startDate.availableText()
            viewModel.endDate = event.endDate.availableText()

            viewModel.totalDays = "${getTotalDayBetweenDates(
                event.startDateTimestamp.toLong().getCalendarRef(),
                event.endDateTimestamp.toLong().getCalendarRef())} days"

            allDetailsGroup.show()
            noBookingGroup.hide()

        } else {
            allDetailsGroup.hide()
            noBookingGroup.show()
        }

        mainFab.show()
    }

    override fun onDeleteClicked() {

        val dialog = context!!.showDialog(
            getString(R.string.delete_booking)
            , getString(R.string.delete_booking_msg),
            getString(R.string.cancel))

        dialog.setPositiveButton(getString(R.string.delete)) { mDialog, _ ->
            val snackbar = parent.showIndefiniteSnack(getString(R.string.deleting_wait_msg))

            viewModel.deleteEvent(event)
                .observe(this, Observer {
                    snackbar!!.dismiss()
                    if (it == SUCCESS) {
                        session.lastOpenedMonthTimestamp(0)
                        parent.showSnack(getString(R.string.successfully_deleted))
                        view!!.findNavController()
                            .navigate(R.id.action_eventDetailFragment_to_calendarViewFragment)
                    } else
                        parent.showSnack(it)
                })
            mDialog.dismiss()
        }.run { show() }

        dialog.setOnDismissListener { resetFab() }
    }

    override fun onMainFabClicked() {
        if (safeArgs.event == null) {

            val navController = view!!.findNavController()
            val action = EventDetailFragmentDirections
                .actionEventDetailToNewBooking()
            action.startTimestamp = safeArgs.openedDateTimestamp
            navController.navigate(action)

        } else {
            if (editFab.isVisible)
                resetFab()
            else
                openFab()
        }
    }

    override fun onEditClicked() {
        val navController = view!!.findNavController()
        val action = EventDetailFragmentDirections
            .actionEventDetailToNewBooking()
        action.eventToUpdate = event
        action.startTimestamp = safeArgs.openedDateTimestamp

        navController.navigate(action)
    }

    private fun resetFab() {
        mainFab.setImageResource(R.drawable.ic_add)
        editFab.hide()
        deleteFab.hide()
    }

    private fun openFab() {
        mainFab.setImageResource(R.drawable.ic_close)
        editFab.show()
        deleteFab.show()
    }
}
