package com.example.playlistmaker.di

import com.example.playlistmaker.db.domain.FavoritesTracksInteractor
import com.example.playlistmaker.db.domain.FavoritesTracksInteractorImpl
import com.example.playlistmaker.db.domain.PlaylistInteractor
import com.example.playlistmaker.db.domain.PlaylistInteractorImpl
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractor
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractorImpl
import com.example.playlistmaker.search.domain.interactor.TrackPrefencesInteractorImpl
import com.example.playlistmaker.search.domain.interactor.TrackPreferencesInteractor
import com.example.playlistmaker.settings.domain.interactor.ThemePreferencesInteractor
import com.example.playlistmaker.settings.domain.interactor.ThemePreferencesInteractorImpl
import com.example.playlistmaker.sharing.domain.interactor.SharingInteractor
import com.example.playlistmaker.sharing.domain.interactor.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<GetTracksInteractor> {
        GetTracksInteractorImpl(get())
    }

    single<TrackPreferencesInteractor> {
        TrackPrefencesInteractorImpl(get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
    single<ThemePreferencesInteractor> {
        ThemePreferencesInteractorImpl(get())
    }
    single<FavoritesTracksInteractor> {
        FavoritesTracksInteractorImpl(get())
    }

    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get ())
    }

}