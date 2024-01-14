package com.example.playlistmaker.settings.data.preferences

import com.example.playlistmaker.search.data.preferences.DayNightThemesPrefs
import com.example.playlistmaker.settings.domain.ThemesPreferences

class ThemesPreferencesImpl : ThemesPreferences {

    override fun getTheme(): Boolean {
        return DayNightThemesPrefs.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        DayNightThemesPrefs.putTheme(putThemePref)
    }

}