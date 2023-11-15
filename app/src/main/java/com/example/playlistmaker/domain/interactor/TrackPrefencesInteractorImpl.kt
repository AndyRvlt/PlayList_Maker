package com.example.playlistmaker.domain.interactor

import android.content.SharedPreferences
import com.example.playlistmaker.data.repository.TrackPreferencesRepositoryImpl
import com.example.playlistmaker.domain.models.TrackDataHandler

class TrackPrefencesInteractorImpl : TrackPreferencesInteractor {

    private val trackPreferencesRepositoryImpl = TrackPreferencesRepositoryImpl()

    override fun cleanHistory(sharedPreferences: SharedPreferences) {
        trackPreferencesRepositoryImpl.cleanHistory(sharedPreferences)
    }

    override fun getTrackPreferences(sharedPreferences: SharedPreferences): TrackDataHandler {
        return trackPreferencesRepositoryImpl.getTrackPreferences(sharedPreferences)
    }

    override fun write(trackDataHandler: TrackDataHandler,sharedPreferences: SharedPreferences) {
        trackPreferencesRepositoryImpl.write(trackDataHandler,sharedPreferences)
    }
}
