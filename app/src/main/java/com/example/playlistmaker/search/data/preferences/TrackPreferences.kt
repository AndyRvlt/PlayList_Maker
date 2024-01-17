package com.example.playlistmaker.search.data.preferences

import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.google.gson.Gson

class TrackPreferences(
    private  val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

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

    companion object {
        private const val TRACK = "track"
    }

}