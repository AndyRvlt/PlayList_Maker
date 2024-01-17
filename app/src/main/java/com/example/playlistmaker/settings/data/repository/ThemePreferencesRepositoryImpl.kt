package com.example.playlistmaker.settings.data.repository

import com.example.playlistmaker.settings.domain.ThemesPreferences
import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository


class ThemePreferencesRepositoryImpl(
    private val themesPreferencesImpl : ThemesPreferences
) : ThemePreferencesRepository {

    override fun getTheme(): Boolean {
        return themesPreferencesImpl.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        themesPreferencesImpl.putTheme(putThemePref)
    }
}