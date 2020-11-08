package com.websoftq.socialnetwork.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.websoftq.socialnetwork.R
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val navController = Navigation.findNavController(this, R.id.menu_nav_host_fragment)
        bottom_nav_view.setItemIconTintList(null);
        setupBottomNavMenu(navController)
    }


    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav_view.let { nav ->
            NavigationUI.setupWithNavController(nav, navController)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this, R.id.menu_nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item!!, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    fun showBottoNavView() {
        bottom_nav_view.visibility = View.VISIBLE
    }

    fun hideBottomNavView() {
        bottom_nav_view.visibility = View.GONE
    }

    fun updateStatusBarColor(color: String?) { // Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.parseColor(color))
        }
    }
}