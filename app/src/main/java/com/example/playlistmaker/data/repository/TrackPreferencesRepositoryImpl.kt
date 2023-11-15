package com.example.playlistmaker.data.repository

import android.content.SharedPreferences
import com.example.playlistmaker.data.preferences.TrackPreferences
import com.example.playlistmaker.domain.models.TrackDataHandler
import com.example.playlistmaker.domain.repository.PreferencesRepository

class TrackPreferencesRepositoryImpl : PreferencesRepository {


    override fun cleanHistory(sharedPreferences: SharedPreferences) {
        val trackDataHandler = TrackPreferences.read(sharedPreferences)
        trackDataHandler.tracks.clear()
        TrackPreferences.write(sharedPreferences, trackDataHandler)

    }

    override fun getTrackPreferences(sharedPreferences: SharedPreferences): TrackDataHandler {
        val trackDataHandler = TrackPreferences.read(sharedPreferences)
        return trackDataHandler

    }

    override fun write(trackDataHandler: TrackDataHandler,sharedPreferences: SharedPreferences) {
        TrackPreferences.write(sharedPreferences, trackDataHandler)
    }
}
