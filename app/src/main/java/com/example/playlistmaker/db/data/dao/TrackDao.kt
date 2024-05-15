package com.example.playlistmaker.db.data.dao

import androidx.room.*
import com.example.playlistmaker.db.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(entity = TrackEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTracks(trackEntity: TrackEntity)

    @Delete (entity = TrackEntity::class)
    fun deleteFavoriteTrack(trackEntity: TrackEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getTracksEntity(): List<TrackEntity>

    @Query("SELECT trackId FROM track_table")
    suspend fun getTracksIdList(): List<String>

}
