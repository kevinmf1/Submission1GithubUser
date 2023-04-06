package com.kevinmedia.submission1githubuser.userInterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.kevinmedia.submission1githubuser.R
import com.kevinmedia.submission1githubuser.SettingPreferences
import com.kevinmedia.submission1githubuser.databinding.ActivitySettingBinding
import com.kevinmedia.submission1githubuser.viewModels.SettingThemeViewModel
import com.kevinmedia.submission1githubuser.viewModels.SettingThemeViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            SettingThemeViewModelFactory(pref)
        )[SettingThemeViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this)
        { isDarkModeActive: Boolean ->
            setNightModeSum(isDarkModeActive)
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchNightmode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchNightmode.isChecked = false
            }
        }

        binding.switchNightmode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
            setNightModeSum(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setNightModeSum(isNightMode: Boolean) {
        binding.tvNightMode.text =
            if (isNightMode) resources.getString(R.string.descriptionOffNightMode) else resources.getString(
                R.string.descriptionNightMode
            )
    }

}