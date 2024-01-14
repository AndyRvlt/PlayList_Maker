package com.example.playlistmaker.search.data.preferences

import com.example.playlistmaker.Creator
import com.example.playlistmaker.KEY_THEMES

object DayNightThemesPrefs {

    private val prefs = Creator.createDayNightThemesPrefs()

     fun getTheme(): Boolean {
        return prefs.getBoolean(KEY_THEMES, false)
    }

     fun putTheme(putThemePref: Boolean) {
        prefs.edit().putBoolean(KEY_THEMES, putThemePref).apply()
    }

}