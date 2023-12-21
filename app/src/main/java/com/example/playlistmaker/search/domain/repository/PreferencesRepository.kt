package com.example.playlistmaker.search.domain.repository

import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.models.TrackDataHandler

interface PreferencesRepository {

    fun cleanHistory(sharedPreferences: SharedPreferences)

    fun getTrackPreferences(sharedPreferences: SharedPreferences): TrackDataHandler

    fun write(trackDataHandler: TrackDataHandler, sharedPreferences: SharedPreferences)

}