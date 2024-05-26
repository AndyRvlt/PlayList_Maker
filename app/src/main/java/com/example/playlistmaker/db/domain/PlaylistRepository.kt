package com.example.playlistmaker.db.domain

import com.example.playlistmaker.db.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    fun addPlaylist(playlist: PlayList)

    fun deletePlaylist(playlist: PlayList)

    fun getPlaylists(): Flow<List<PlayList>>
}