package com.arkapp.gyanvatika.ui.home.calendarView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.databinding.FragmentCalendarViewBinding

class CalendarViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentCalendarViewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calendar_view, container, false)

        val viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}
