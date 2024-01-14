package com.example.playlistmaker.settings.domain.interactor

import com.example.playlistmaker.Creator


class ThemePreferencesInteractorImpl : ThemePreferencesInteractor {

    private val themePreferencesRepoImpl = Creator.createThemePreferencesRepository()

    override fun getTheme(): Boolean {
        return themePreferencesRepoImpl.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        themePreferencesRepoImpl.putTheme(putThemePref)
    }

}