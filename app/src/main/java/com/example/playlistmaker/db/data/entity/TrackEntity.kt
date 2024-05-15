package com.example.playlistmaker.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "track_table")
data class TrackEntity (
    @PrimaryKey
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val trackId: String,
    val collectionName : String,
    val releaseDate: String,
    val primaryGenreName : String,
    val country: String,
    val previewUrl: String
)
