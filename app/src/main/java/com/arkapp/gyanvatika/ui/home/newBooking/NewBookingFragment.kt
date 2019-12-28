package com.arkapp.gyanvatika.ui.home.newBooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.databinding.FragmentNewBookingBinding

class NewBookingFragment : Fragment() {

    private lateinit var viewModel: NewBookingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentNewBookingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_booking, container, false)

        viewModel = ViewModelProviders.of(this).get(NewBookingViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}
