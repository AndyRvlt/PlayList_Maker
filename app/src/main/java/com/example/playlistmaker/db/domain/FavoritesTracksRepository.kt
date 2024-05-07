package com.example.playlistmaker.db.domain



import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesTracksRepository {

    fun addFavoriteTrack(track: Track)

    fun deleteFavoriteTrack(track:Track)

    suspend fun getFavoritesTracks(): Flow<List<Track>>

}