package com.example.playlistmaker.settings.data.repository


import android.content.Context
import com.example.playlistmaker.settings.data.preferences.ThemesPreferencesImpl
import com.example.playlistmaker.settings.domain.ThemesPreferences
import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository


class ThemePreferencesRepositoryImpl : ThemePreferencesRepository {

    lateinit var themesPreferencesImpl: ThemesPreferences

    fun init(context: Context) {
        themesPreferencesImpl = ThemesPreferencesImpl(context)
    }

    override fun getTheme(): Boolean {
        val getThemePreferences = themesPreferencesImpl.getTheme()
        return getThemePreferences
    }

    override fun putTheme(putThemePref: Boolean) {
        themesPreferencesImpl.putTheme(putThemePref)
    }
}