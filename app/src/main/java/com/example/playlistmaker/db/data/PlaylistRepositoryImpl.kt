package com.example.playlistmaker.db.data

import com.example.playlistmaker.db.PlaylistDatabase
import com.example.playlistmaker.db.data.entity.PlaylistEntity
import com.example.playlistmaker.db.domain.PlaylistRepository
import com.example.playlistmaker.db.domain.models.PlayList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val playlistDatabase: PlaylistDatabase,
    private val playlistDbConvertor: PlaylistDbConvertor
) : PlaylistRepository {

    override fun addPlaylist(playlist: PlayList) {
        val playlistEntity = playlistDbConvertor.playlistMap(playlist)
        playlistDatabase.playlistDao().insertPlaylist(playlistEntity)
    }

    override fun deletePlaylist(playlist: PlayList) {
        val playlistEntity = playlistDbConvertor.playlistMap(playlist)
        playlistDatabase.playlistDao().deletePlaylist(playlistEntity)
    }

    override fun getPlaylists(): Flow<List<PlayList>> = flow {
        val playlists = playlistDatabase.playlistDao().getPlaylists().reversed()
        emit(converterFromPlaylistsEntity(playlists))
    }

    private fun converterFromPlaylistsEntity(playlists: List<PlaylistEntity>): List<PlayList> {
        return playlists.map { playlist -> playlistDbConvertor.playlistMap(playlist) }
    }
}