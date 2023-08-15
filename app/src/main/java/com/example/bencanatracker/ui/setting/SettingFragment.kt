package com.example.bencanatracker.ui.setting

import ViewModelFactory
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bencanatracker.R
import com.example.bencanatracker.SettingPreferences
import com.example.bencanatracker.dataStore
import com.example.bencanatracker.response.FloodResponse
import com.example.bencanatracker.response.Geometrys
import com.example.bencanatracker.response.Objectss
import com.example.bencanatracker.response.Outputs
import com.example.bencanatracker.response.Properties
import com.example.bencanatracker.response.Results
import com.example.bencanatracker.response.Transform
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
        private const val PERMISSION_REQUEST_CODE = 123
        private const val NOTIFICATION_ID = 123
    }

    private lateinit var viewModel: SettingViewModel
    private lateinit var loadingLayout: View
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        loadingLayout = root.findViewById(R.id.loadingLayout)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val switchTheme = view?.findViewById<SwitchMaterial>(R.id.switch_theme)
        val triggerNotificationButton = view?.findViewById<Button>(R.id.btn_trigger_notification)

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref, requireContext())).get(
            SettingViewModel::class.java
        )


        loadingLayout.visibility = View.VISIBLE

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme?.isChecked = false
            }

            loadingLayout.visibility = View.GONE
        }

        switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        triggerNotificationButton?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.INTERNET
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.INTERNET),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                // Permission already granted, proceed with showing the notification
                val fakeResponse = createFakeFloodResponse() // Replace with your data
                val pendingIntent = createPendingIntent() // Replace with your PendingIntent logic
                settingViewModel.processFloodData(fakeResponse, pendingIntent)
            }
        }
    }

    private fun createFakeFloodResponse(): FloodResponse {
        val fakeGeometry = Geometrys(
            type = "Polygon",
            properties = Properties(
                areaId = "5",
                geomId = "3174040004009000",
                areaName = "RW 09",
                parentName = "GROGOL",
                cityName = "Jakarta",
                state = 1,
                lastUpdated = "2016-12-19T13:53:52.274Z"
            ),
            arcs = listOf(listOf(0))
        )

        val fakeOutput = Outputs(
            type = "GeometryCollection",
            geometries = listOf(fakeGeometry)
        )

        val fakeObjects = Objectss(output = fakeOutput)

        val fakeResult = Results(
            type = "Topology",
            objects = fakeObjects,
            arcs = listOf(
                listOf(
                    listOf(9999, 7847),
                    listOf(-507, -6),
                    // ... (other arcs data)
                )
            ),
            transform = Transform(
                scale = listOf(0.0000003311331833192323, 0.00000032713269326930546),
                translate = listOf(106.7917869997, -6.158925)
            ),
            bbox = listOf(106.7917869997, -6.158925, 106.7950980004, -6.1556540002)
        )

        return FloodResponse(
            statusCode = 200,
            result = fakeResult
        )
    }


    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(requireContext(), SettingFragment::class.java)
        return PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val fakeResponse = createFakeFloodResponse() // Replace with data
                val pendingIntent = createPendingIntent() // Replace with PendingIntent logic
                settingViewModel.processFloodData(fakeResponse, pendingIntent)
            } else {
                // Handle permission denied
            }
        }
    }
}
