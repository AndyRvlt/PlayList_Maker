package com.example.playlistmaker.settings.domain

import android.content.Context
import com.example.playlistmaker.settings.data.repository.ThemePreferencesRepositoryImpl
import kotlin.Boolean


class ThemePreferencesInteractorImpl : ThemePreferencesInteractor {

    private val themePreferencesRepoImpl = ThemePreferencesRepositoryImpl()

    override fun init(context: Context) {
        themePreferencesRepoImpl.init(context)
    }

    override fun getTheme(): Boolean {
        return themePreferencesRepoImpl.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        themePreferencesRepoImpl.putTheme(putThemePref)
    }

}