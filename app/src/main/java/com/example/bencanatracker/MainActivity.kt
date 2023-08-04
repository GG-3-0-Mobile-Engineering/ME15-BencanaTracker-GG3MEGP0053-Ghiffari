package com.example.bencanatracker

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bencanatracker.databinding.ActivityMainBinding
import com.example.bencanatracker.ui.map.MapFragment
import com.example.bencanatracker.ui.reports.ReportsFragment

class MainActivity : AppCompatActivity(), MapFragment.OnResetClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_reports, R.id.navigation_Setting
            )
        )

        //

        //

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResetButtonClick() {
        val reportsFragment =
            supportFragmentManager.findFragmentByTag("fragment_reports") as? ReportsFragment
        reportsFragment?.resetRecyclerView()
    }
}
