package com.example.playlistmaker.settings.domain.interactor

interface ThemePreferencesInteractor {

    fun getTheme(): Boolean

    fun putTheme (putThemePref: Boolean)
}