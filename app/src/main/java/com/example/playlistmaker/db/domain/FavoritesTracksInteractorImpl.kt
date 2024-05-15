package com.example.playlistmaker.db.domain


import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow


class FavoritesTracksInteractorImpl(
    val favoritesTracksRepository: FavoritesTracksRepository
) : FavoritesTracksInteractor {


    override suspend fun getFavoritesTracks(): Flow<List<Track>> {
        return favoritesTracksRepository.getFavoritesTracks()
    }

    override fun addFavoriteTrack(track: Track) {
        favoritesTracksRepository.addFavoriteTrack(track)
    }

    override fun deleteFavoriteTrack(track: Track) {
        favoritesTracksRepository.deleteFavoriteTrack(track)
    }

}