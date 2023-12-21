package com.example.playlistmaker.settings.domain


interface ThemesPreferences {

    fun getTheme(): Boolean

    fun putTheme(putThemePref : Boolean)
}