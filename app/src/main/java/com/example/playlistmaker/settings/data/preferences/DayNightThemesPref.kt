package com.example.playlistmaker.settings.data.preferences

import android.content.SharedPreferences

class DayNightThemesPrefs(
    private val prefs: SharedPreferences
) {
    private val KEY_THEMES = "key_themes"

    fun getTheme(): Boolean {
        return prefs.getBoolean(KEY_THEMES, false)
    }

    fun putTheme(putThemePref: Boolean) {
        prefs.edit().putBoolean(KEY_THEMES, putThemePref).apply()
    }

}