package com.arkapp.gyanvatika.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.data.preferences.PrefSession
import com.arkapp.gyanvatika.databinding.ActivityHomeBinding
import com.arkapp.gyanvatika.ui.splashscreen.SplashViewModel
import com.arkapp.gyanvatika.ui.splashscreen.SplashViewModelFactory
import com.arkapp.gyanvatika.utils.RC_SIGN_IN
import com.arkapp.gyanvatika.utils.showSnack
import com.arkapp.gyanvatika.utils.showSnackLong
import com.google.firebase.auth.FirebaseAuth
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val session: PrefSession by instance()

    private val factory: SplashViewModelFactory by instance()

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel::class.java)
        binding.viewmodel = viewModel

        val navController = findNavController(R.id.mainNavigationFragment)
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                parentLay.showSnack("Welcome ${user?.displayName!!}")
                session.showBookingAmount(true)//initializing the setting
                viewModel.openApp()
            } else {
                parentLay.showSnackLong(getString(R.string.sign_in_msg))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.mainNavigationFragment).navigateUp()
    }

    fun hideBottomNavigation() {
        bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bottomNavigation.visibility = View.VISIBLE
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
}
