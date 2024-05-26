package com.example.playlistmaker.db.data.dao

import androidx.room.*
import com.example.playlistmaker.db.data.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert(entity = PlaylistEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Update
    fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Delete
    fun deletePlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlaylists(): List<PlaylistEntity>

}