package com.arkapp.gyanvatika.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.databinding.FragmentSplashBinding
import kotlinx.android.synthetic.main.activity_home.*

class SplashFragment : Fragment(), SplashDataListener {

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSplashBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container, false)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        viewModel.dataListener = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchBookings()
    }

    override fun onDataFetched() {
        val navController = view!!.findNavController()
        navController.navigate(R.id.openHomeScreen)
    }

    override fun onFailed() {
    }

}
