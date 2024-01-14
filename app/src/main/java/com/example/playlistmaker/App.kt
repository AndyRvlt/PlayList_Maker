package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.settings.data.repository.ThemePreferencesRepositoryImpl
import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository

const val APP_SETTINGS_DAY_NIGHT_THEMES = "app_settings_day_night_themes"
const val KEY_THEMES = "key_themes"

class App : Application() {


    private lateinit var repo: ThemePreferencesRepository

    override fun onCreate() {
        super.onCreate()

        Creator.setApplication(this@App)

        repo = ThemePreferencesRepositoryImpl()
        val themeApp = repo.getTheme()
        switchTheme(themeApp)
    }

    fun switchTheme(darkThemeEnable: Boolean) {
        repo.putTheme(darkThemeEnable)

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnable) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

        )
    }
}