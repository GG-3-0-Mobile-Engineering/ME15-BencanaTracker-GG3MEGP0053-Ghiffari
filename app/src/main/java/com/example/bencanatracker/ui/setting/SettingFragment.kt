package com.example.bencanatracker.ui.setting

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.bencanatracker.R
import com.example.bencanatracker.SettingPreferences
import com.example.bencanatracker.ViewModelFactory
import com.example.bencanatracker.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val switchTheme = view?.findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme?.isChecked = false
            }
        }

        switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

}
