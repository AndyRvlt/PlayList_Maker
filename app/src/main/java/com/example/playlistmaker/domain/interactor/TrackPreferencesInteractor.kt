package com.example.playlistmaker.domain.interactor

import android.content.SharedPreferences
import com.example.playlistmaker.domain.models.TrackDataHandler

interface TrackPreferencesInteractor {

    fun cleanHistory(sharedPreferences: SharedPreferences)


    fun getTrackPreferences(sharedPreferences: SharedPreferences): TrackDataHandler


    fun write(trackDataHandler: TrackDataHandler,sharedPreferences: SharedPreferences)

}
