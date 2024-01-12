package com.example.playlistmaker.search.domain.interactor

import android.content.Context
import com.example.playlistmaker.search.domain.models.TrackDataHandler

interface TrackPreferencesInteractor {

    fun cleanHistory(context: Context)


    fun getTrackPreferences(context: Context): TrackDataHandler


    fun write(context: Context, trackDataHandler: TrackDataHandler)

}
