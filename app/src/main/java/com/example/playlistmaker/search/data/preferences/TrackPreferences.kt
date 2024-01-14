package com.example.playlistmaker.search.data.preferences

import com.example.playlistmaker.Creator
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.google.gson.Gson

object TrackPreferences {

    private val sharedPreferences = Creator.createTrackPreference()
    private val gson =  Gson()


    const val TRACK = "track"

    fun read(): TrackDataHandler {
        val json = sharedPreferences.getString(
            TRACK, null
        ) ?: return TrackDataHandler(mutableListOf())
        return gson.fromJson(json, TrackDataHandler::class.java)
    }

    fun write(trackHandler: TrackDataHandler) {
        val json = gson.toJson(trackHandler)
        sharedPreferences.edit().putString(TRACK, json).apply()
    }

}