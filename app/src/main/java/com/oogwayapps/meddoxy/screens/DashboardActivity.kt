package com.oogwayapps.meddoxy.screens

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oogwayapps.meddoxy.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navController = this.findNavController(R.id.navHostFragmentDashboard)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavBarView)
        navView.setupWithNavController(navController)

        val colorDrawable = ColorDrawable(Color.parseColor("#113137"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
    }
}