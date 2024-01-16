package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.search.data.preferences.TrackPreferences
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository


class TrackPreferencesRepositoryImpl : TrackPreferencesRepository {

    override fun cleanHistory() {
        val trackDataHandler = TrackPreferences.read()
        trackDataHandler.tracks.clear()
        TrackPreferences.write(trackDataHandler)
    }

    override fun getTrackPreferences(): TrackDataHandler {
        return TrackPreferences.read()
    }

    override fun write(trackDataHandler: TrackDataHandler) {
        TrackPreferences.write(trackDataHandler)
    }
}
