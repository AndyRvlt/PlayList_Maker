package com.example.playlistmaker.db.domain


import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesTracksInteractor {

   suspend fun getFavoritesTracks(): Flow<List<Track>>

    fun addFavoriteTrack(track: Track)

    fun deleteFavoriteTrack(track: Track)

}