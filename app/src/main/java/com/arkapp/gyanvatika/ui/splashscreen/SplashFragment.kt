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
import com.arkapp.gyanvatika.ui.home.HomeActivity
import com.arkapp.gyanvatika.utils.openLoginScreen
import com.arkapp.gyanvatika.utils.show
import kotlinx.android.synthetic.main.fragment_splash.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class SplashFragment : Fragment(), SplashDataListener, KodeinAware {

    override val kodeinContext = kcontext<Fragment>(this)

    override val kodein by kodein()

    private val factory: SplashViewModelFactory by instance()

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSplashBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container, false)

        viewModel = ViewModelProviders.of(activity!!, factory).get(SplashViewModel::class.java)

        viewModel.dataListener = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).hideBottomNavigation()
        viewModel.isUserLoggedIn()
    }

    override fun showLogin() {
        loginBtn.show()
    }

    override fun startLogin() {
        openLoginScreen(activity!!)
    }

    override fun openApp() {
        try {
            val navController = view!!.findNavController()
            navController.navigate(R.id.action_splashFragment_to_calendarViewFragment)
        } catch (e: Exception) {
        }
    }
}
