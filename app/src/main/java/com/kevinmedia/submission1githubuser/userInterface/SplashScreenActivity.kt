package com.kevinmedia.submission1githubuser.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.kevinmedia.submission1githubuser.R
import com.kevinmedia.submission1githubuser.SettingPreferences
import com.kevinmedia.submission1githubuser.viewModels.SettingThemeViewModel
import com.kevinmedia.submission1githubuser.viewModels.SettingThemeViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private var startSplash: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val img = findViewById<ImageView>(R.id.imageView)
        val splashScreenTheme = findViewById<ConstraintLayout>(R.id.splashScreen)
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingThemeViewModelFactory(pref))[SettingThemeViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this)
        { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                splashScreenTheme.setBackgroundColor(ContextCompat.getColor(this, androidx.appcompat.R.color.primary_material_dark))
                startSplash?.start()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                splashScreenTheme.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                startSplash?.start()
            }
        }

        startSplash = img.animate().setDuration(splashDelay).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            intent.apply {
                startActivity(this)
                finish()
            }
        }
    }

    override fun onDestroy() {
        startSplash?.cancel()
        super.onDestroy()
    }

    companion object {
        private var splashDelay: Long = 1_000L
    }
}