package com.example.playlistmaker.settings.data.repository


interface ThemePreferencesRepository {

    fun getTheme(): Boolean

    fun putTheme(putThemePref: Boolean)

}