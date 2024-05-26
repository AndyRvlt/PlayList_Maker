package com.example.playlistmaker.db.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.playlistmaker.db.ListTrackContainer
//import com.example.playlistmaker.db.data.TrackJsonConverterForPlaylist
import com.example.playlistmaker.search.domain.models.Track


@Entity(
    tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistId: Long,
    val name: String?,
    val description: String?,
    val imageUri: String?,
//    @TypeConverters(TrackJsonConverterForPlaylist::class)
//    val trackIds: ListTrackContainer,
 //   val trackIds: List<Track> = emptyList(),
    val trackCount: Int = 0
)

