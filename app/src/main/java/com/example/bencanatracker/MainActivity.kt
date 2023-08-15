package com.example.bencanatracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bencanatracker.databinding.ActivityMainBinding
import com.example.bencanatracker.ui.map.MapFragment
import com.example.bencanatracker.ui.reports.ReportsFragment
import com.example.bencanatracker.ui.setting.SettingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MapFragment.OnResetClickListener {

    private lateinit var binding: ActivityMainBinding
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_reports, R.id.navigation_Setting
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResetButtonClick() {
        val reportsFragment =
            supportFragmentManager.findFragmentByTag("fragment_reports") as? ReportsFragment
        reportsFragment?.resetRecyclerView()
    }
}
