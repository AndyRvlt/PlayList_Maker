package com.example.playlistmaker.settings.domain.repository


interface ThemePreferencesRepository {

    fun getTheme(): Boolean

    fun putTheme(putThemePref: Boolean)

}