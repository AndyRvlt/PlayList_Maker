package com.example.playlistmaker.db.domain

import com.example.playlistmaker.db.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    val playlistRepository: PlaylistRepository
) : PlaylistInteractor {
    override fun addPlaylist(playlist: PlayList) {
        playlistRepository.addPlaylist(playlist)
    }

    override fun deletePlaylist(playlist: PlayList) {
        playlistRepository.deletePlaylist(playlist)
    }

    override fun getPlaylists(): Flow<List<PlayList>> {
        return playlistRepository.getPlaylists()
    }
}