package com.example.playlistmaker


import android.app.Application
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.search.data.network.NetworkClient
import com.example.playlistmaker.search.data.network.RetrofitNetworkClient
import com.example.playlistmaker.search.data.preferences.TrackPreferences
import com.example.playlistmaker.search.data.repository.TrackPreferencesRepositoryImpl
import com.example.playlistmaker.search.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractor
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractorImpl
import com.example.playlistmaker.search.domain.interactor.TrackPrefencesInteractorImpl
import com.example.playlistmaker.search.domain.interactor.TrackPreferencesInteractor
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository
import com.example.playlistmaker.search.domain.repository.TrackRepository
import com.example.playlistmaker.settings.data.preferences.ThemesPreferencesImpl
import com.example.playlistmaker.settings.data.repository.ThemePreferencesRepositoryImpl
import com.example.playlistmaker.settings.domain.repository.ThemePreferencesRepository
import com.example.playlistmaker.sharing.data.repository.ExternalNavigatorRepositoryImpl
import com.example.playlistmaker.sharing.domain.repository.ExternalNavigatorRepository

object Creator {

    private lateinit var application: Application

    fun createPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    fun createRetrofitNetworkClient(): NetworkClient {
        return RetrofitNetworkClient()
    }

    fun setApplication(application: Application) {
        this.application = application
    }

    fun createThemePreferences(): ThemesPreferencesImpl {
        return ThemesPreferencesImpl()
    }

    fun createTrackPreference(): SharedPreferences {
        return application
            .applicationContext
            .getSharedPreferences(TrackPreferences.TRACK, AppCompatActivity.MODE_PRIVATE)
    }

    fun createTrackPreferencesRepository(): TrackPreferencesRepository {
        return TrackPreferencesRepositoryImpl()
    }

    fun createThemePreferencesRepository(): ThemePreferencesRepository {
        return ThemePreferencesRepositoryImpl()
    }

    fun createContextProvider(): ContextProvider {
        return ContextProviderImpl(application.applicationContext)
    }

    fun createExternalNavigatorRepository(): ExternalNavigatorRepository {
        return ExternalNavigatorRepositoryImpl()
    }

    fun createDayNightThemesPrefs(): SharedPreferences {
        return application.getSharedPreferences(APP_SETTINGS_DAY_NIGHT_THEMES, AppCompatActivity.MODE_PRIVATE)
    }

    fun createTrackRepository(): TrackRepository{
        return TrackRepositoryImpl()
    }

    fun createGetTracksInteractor(): GetTracksInteractor {
        return GetTracksInteractorImpl(createTrackRepository())
    }

    fun createTrackPrefencesInteractor(): TrackPreferencesInteractor {
        return TrackPrefencesInteractorImpl(createTrackPreferencesRepository())
    }


}