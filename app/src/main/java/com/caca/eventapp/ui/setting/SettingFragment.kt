package com.caca.eventapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.caca.eventapp.data.local.preference.SettingPreferences
import com.caca.eventapp.data.local.preference.dataStore
import com.caca.eventapp.databinding.FragmentSettingBinding
import com.caca.eventapp.utils.factory.ViewModelFactoryWithSettingPreferences

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        // Define ViewModel
        val settingViewModel = ViewModelProvider(this, ViewModelFactoryWithSettingPreferences(pref)).get(
            SettingViewModel::class.java
        )

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root : View = binding.root

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                _binding?.switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                _binding?.switchTheme?.isChecked = false
            }
        }

        _binding?.switchTheme?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }



        return root
    }
}


