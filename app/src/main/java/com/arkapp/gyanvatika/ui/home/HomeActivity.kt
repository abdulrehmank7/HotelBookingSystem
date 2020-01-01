package com.arkapp.gyanvatika.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.arkapp.gyanvatika.R
import com.arkapp.gyanvatika.utils.setupCalligraphy
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navController = findNavController(R.id.mainNavigationFragment)
        bottomNavigation.setupWithNavController(navController)
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
