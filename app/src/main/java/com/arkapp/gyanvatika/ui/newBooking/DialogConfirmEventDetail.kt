package com.arkapp.gyanvatika.ui.newBooking

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.utils.*
import kotlinx.android.synthetic.main.dialog_confirm_event_detail.*
import kotlinx.android.synthetic.main.layout_progress_bar.*

/**
 * Created by Abdul Rehman on 2/15/2019.
 */
class DialogConfirmEventDetail(context: Context, private val eventViewModel: NewBookingViewModel) : Dialog(context) {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_confirm_event_detail)
        val parent = window!!.decorView
        parent.setBackgroundResource(android.R.color.transparent)

        customerNameTv.text = eventViewModel.customerName.availableText()
        customerPhoneTv.text = eventViewModel.customerPhone.availableText()
        bookingAmountTv.text = eventViewModel.bookingAmount.availableText()
        otherInfoTv.text = eventViewModel.otherInfo.availableText()

        startDateTv.text = formatDateFromCalendar(eventViewModel.startDateCalendar)
        endDateTv.text = formatDateFromCalendar(eventViewModel.endDateCalendar)

        totalDaysTv.text = "${getTotalDayBetweenDates(
            eventViewModel.startDateCalendar,
            eventViewModel.endDateCalendar
        )} days"


        conformBtn.setOnClickListener {
            if (isNotDoubleClicked(1000)) {
                progressBar.show()
                conformBtn.isEnabled = false
                eventViewModel.getEventForCheckingExistingBooking()
            }
        }
    }
}
