package com.example.playlistmaker.search.domain.interactor

import android.content.Context
import com.example.playlistmaker.search.data.repository.TrackPreferencesRepositoryImpl
import com.example.playlistmaker.search.domain.models.TrackDataHandler

class TrackPrefencesInteractorImpl() : TrackPreferencesInteractor {

    private val trackPreferencesRepositoryImpl = TrackPreferencesRepositoryImpl()

    override fun cleanHistory(context: Context) {
        trackPreferencesRepositoryImpl.cleanHistory(context)
    }

    override fun getTrackPreferences(context: Context): TrackDataHandler {
        return trackPreferencesRepositoryImpl.getTrackPreferences(context)
    }

    override fun write(context: Context, trackDataHandler: TrackDataHandler) {
        trackPreferencesRepositoryImpl.write(context,trackDataHandler)
    }
}
