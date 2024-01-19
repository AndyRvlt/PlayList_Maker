package com.example.playlistmaker.settings.data.preferences

import com.example.playlistmaker.settings.domain.ThemesPreferences

class ThemesPreferencesImpl(
   private val dayNightThemesPrefs: DayNightThemesPrefs
) : ThemesPreferences {

    override fun getTheme(): Boolean {
        return dayNightThemesPrefs.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        dayNightThemesPrefs.putTheme(putThemePref)
    }

}