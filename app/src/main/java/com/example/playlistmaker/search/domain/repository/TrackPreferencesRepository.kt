package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.search.domain.models.TrackDataHandler

interface TrackPreferencesRepository {

    fun cleanHistory()

    fun getTrackPreferences(): TrackDataHandler

    fun write(trackDataHandler: TrackDataHandler)

}