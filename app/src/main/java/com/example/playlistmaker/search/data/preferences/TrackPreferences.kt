package com.example.playlistmaker.search.data.preferences


import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.google.gson.Gson

object TrackPreferences {
    private const val TRACK = "track"

    private fun init (context: Context): SharedPreferences{
      return context.getSharedPreferences(TRACK, AppCompatActivity.MODE_PRIVATE)
    }

    fun read(context: Context): TrackDataHandler {

        val json = init(context).getString(TRACK, null) ?: return TrackDataHandler(
            mutableListOf()
            )
        return Gson().fromJson(json, TrackDataHandler::class.java)
    }

    fun write(context: Context, trackHandler: TrackDataHandler) {
        val json = Gson().toJson(trackHandler)
        init(context).edit().putString(TRACK, json).apply()
    }

}