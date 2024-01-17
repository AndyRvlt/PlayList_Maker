package com.example.playlistmaker.settings.domain.interactor

import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository


class ThemePreferencesInteractorImpl(
    private val themePreferencesRepoImpl: ThemePreferencesRepository
) : ThemePreferencesInteractor {

    override fun getTheme(): Boolean {
        return themePreferencesRepoImpl.getTheme()
    }

    override fun putTheme(putThemePref: Boolean) {
        themePreferencesRepoImpl.putTheme(putThemePref)
    }

}