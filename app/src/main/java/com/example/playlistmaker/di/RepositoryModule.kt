package com.example.playlistmaker.di

import com.example.playlistmaker.db.data.FavoritesTracksRepositoryImpl
import com.example.playlistmaker.db.data.TrackDbConvertor
import com.example.playlistmaker.db.domain.FavoritesTracksRepository
import com.example.playlistmaker.search.data.repository.TrackPreferencesRepositoryImpl
import com.example.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository
import com.example.playlistmaker.search.domain.repository.TrackRepository
import com.example.playlistmaker.settings.data.repository.ThemePreferencesRepositoryImpl
import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository
import com.example.playlistmaker.sharing.data.ExternalNavigatorImpl
import com.example.playlistmaker.sharing.data.repository.ExternalNavigatorRepositoryImpl
import com.example.playlistmaker.sharing.domain.ExternalNavigator
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigatorRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<TrackRepository> {
        TrackRepositoryImpl(get(), get())
    }

    single<TrackPreferencesRepository> {
        TrackPreferencesRepositoryImpl(get())
    }

    single<ThemePreferencesRepository> {
        ThemePreferencesRepositoryImpl(get())
    }

    single<ExternalNavigatorRepository> {
        ExternalNavigatorRepositoryImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
    single<FavoritesTracksRepository> {
        FavoritesTracksRepositoryImpl(get(), get())
    }
    factory { TrackDbConvertor() }
}