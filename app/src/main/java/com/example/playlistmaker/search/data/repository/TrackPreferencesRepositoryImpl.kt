package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.search.data.preferences.TrackPreferences
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository


class TrackPreferencesRepositoryImpl(
    private val trackPreferences: TrackPreferences
) : TrackPreferencesRepository {

    override fun cleanHistory() {
        val trackDataHandler = trackPreferences.read()
        trackDataHandler.tracks.clear()
        trackPreferences.write(trackDataHandler)
    }

    override fun getTrackPreferences(): TrackDataHandler {
        return trackPreferences.read()
    }

    override fun write(trackDataHandler: TrackDataHandler) {
        trackPreferences.write(trackDataHandler)
    }
}
