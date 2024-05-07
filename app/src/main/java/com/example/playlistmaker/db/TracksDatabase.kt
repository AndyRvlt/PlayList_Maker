package com.example.playlistmaker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.db.data.dao.TrackDao
import com.example.playlistmaker.db.data.entity.TrackEntity

@Database(version = 1, entities = [TrackEntity::class])
abstract class TracksDatabase: RoomDatabase() {

    abstract fun trackDao(): TrackDao
}