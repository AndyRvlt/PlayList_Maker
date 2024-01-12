package com.example.playlistmaker.settings.domain.interactor

import android.content.Context
import android.content.SharedPreferences
import kotlin.Boolean

interface ThemePreferencesInteractor {

    fun init(context: Context)

    fun getTheme(): Boolean

    fun putTheme (putThemePref: Boolean)
}