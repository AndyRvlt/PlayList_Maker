package com.example.playlistmaker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
//import com.example.playlistmaker.db.data.TrackJsonConverterForPlaylist
import com.example.playlistmaker.db.data.dao.PlaylistDao
import com.example.playlistmaker.db.data.entity.PlaylistEntity


@Database(version = 1, entities = [PlaylistEntity::class])
//@TypeConverters(TrackJsonConverterForPlaylist::class)
abstract class PlaylistDatabase: RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao
}