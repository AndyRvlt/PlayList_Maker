package com.example.playlistmaker.db.domain.models

import androidx.room.TypeConverters
//import com.example.playlistmaker.db.data.entity.TrackIdListConverter

data class PlayList(
    val id: Long,
    val name: String?,
    val description: String?,
    val imageUri: String?,
   // val trackIds: String?,
    val trackCount: Int = 0
)
