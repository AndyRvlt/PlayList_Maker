package com.example.playlistmaker.search.data.preferences


import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.google.gson.Gson

object TrackPreferences {
    private const val TRACK = "track"

    fun read(sharedPreferences: SharedPreferences): TrackDataHandler {
        val json =
            sharedPreferences.getString(TRACK, null) ?: return TrackDataHandler(
                mutableListOf()
            )
        return Gson().fromJson(json, TrackDataHandler::class.java)
    }

    fun write(sharedPreferences: SharedPreferences, trackHandler: TrackDataHandler) {
        val json = Gson().toJson(trackHandler)
        sharedPreferences.edit().putString(TRACK, json).apply()
    }

}