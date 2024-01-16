package com.example.playlistmaker.search.domain.interactor

import com.example.playlistmaker.search.domain.models.TrackDataHandler

interface TrackPreferencesInteractor {

    fun cleanHistory()

    fun getTrackPreferences(): TrackDataHandler


    fun write(trackDataHandler: TrackDataHandler)

}
