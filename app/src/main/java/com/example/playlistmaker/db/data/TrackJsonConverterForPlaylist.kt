package com.example.playlistmaker.db.data

import androidx.room.TypeConverters
import com.example.playlistmaker.db.ListTrackContainer
import com.example.playlistmaker.search.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//class TrackJsonConverterForPlaylist {
//
//    private val gson = Gson()
//
//    @TypeConverters
//    fun tracksToPlaylist(value: ListTrackContainer): String {
//        return gson.toJson(value)
//    }
//
//    @TypeConverters
//    fun tracksFromPlaylist(value: String): ListTrackContainer {
//        val myType = object : TypeToken<List<Track>>() {}.type
//        val tracks = gson.fromJson<List<Track>>(value, myType)
//        return ListTrackContainer(tracks)
//    }
//}
