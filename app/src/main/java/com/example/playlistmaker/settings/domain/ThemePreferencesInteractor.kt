package com.example.playlistmaker.settings.domain

import android.content.Context
import android.content.SharedPreferences
import kotlin.Boolean

interface ThemePreferencesInteractor {

    fun init(context: Context)

    fun getTheme(): Boolean

    fun putTheme (putThemePref: Boolean)
}