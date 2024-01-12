package com.example.playlistmaker.search.data.repository

import android.content.Context
import com.example.playlistmaker.search.data.preferences.TrackPreferences
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository


class TrackPreferencesRepositoryImpl : TrackPreferencesRepository {



    override fun cleanHistory(context: Context) {

        val trackDataHandler = TrackPreferences.read(context)
        trackDataHandler.tracks.clear()
        TrackPreferences.write(context,trackDataHandler)

    }

    override fun getTrackPreferences(context: Context): TrackDataHandler {
        val trackDataHandler = TrackPreferences.read(context)
        return trackDataHandler

    }

    override fun write(context: Context, trackDataHandler: TrackDataHandler) {
        TrackPreferences.write(context,trackDataHandler)
    }
}
