package com.example.playlistmaker.settings.data.repository

import com.example.playlistmaker.Creator
import com.example.playlistmaker.settings.domain.ThemesPreferences
import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository


class ThemePreferencesRepositoryImpl : ThemePreferencesRepository {

    private val themesPreferencesImpl: ThemesPreferences = Creator.createThemePreferences()

    override fun getTheme(): Boolean {
        return themesPreferencesImpl.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        themesPreferencesImpl.putTheme(putThemePref)
    }
}