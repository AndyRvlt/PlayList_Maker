package com.example.playlistmaker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources.Theme
import androidx.appcompat.app.AppCompatDelegate

const val APP_SETTINGS_DAY_NIGHT_THEMES = "app_settings_day_night_themes"
const val KEY_THEMES = "key_themes"

class App : Application() {

    private var darkTheme = false

    private lateinit var pref: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        pref =
            getSharedPreferences(APP_SETTINGS_DAY_NIGHT_THEMES, Context.MODE_PRIVATE)
        val themeApp = pref.getBoolean(KEY_THEMES, darkTheme)

        switchTheme(themeApp)
    }

    fun switchTheme(darkThemeEnable: Boolean) {

        darkTheme = darkThemeEnable


        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnable) {
                pref.edit().putBoolean(KEY_THEMES, true).apply()
                AppCompatDelegate.MODE_NIGHT_YES

            } else {
                pref.edit().putBoolean(KEY_THEMES, false).apply()
                AppCompatDelegate.MODE_NIGHT_NO
            }

        )
    }
}