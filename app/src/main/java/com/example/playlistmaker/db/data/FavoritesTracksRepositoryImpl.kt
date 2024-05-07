package com.example.playlistmaker.db.data


import com.example.playlistmaker.db.TracksDatabase
import com.example.playlistmaker.db.data.entity.TrackEntity
import com.example.playlistmaker.db.domain.FavoritesTracksRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesTracksRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : FavoritesTracksRepository {

    override fun addFavoriteTrack(track: Track) {
        val trackEntity = trackDbConvertor.map(track)
        tracksDatabase.trackDao().insertFavoriteTracks(trackEntity)

    }

    override fun deleteFavoriteTrack(track: Track) {
        val trackEntity = trackDbConvertor.map(track)
        tracksDatabase.trackDao().deleteFavoriteTrack(trackEntity)
    }

    override suspend fun getFavoritesTracks(): Flow<List<Track>> = flow {
        val favoriteTracks = tracksDatabase.trackDao().getTracksEntity()
        emit(converterFromTracksEntity(favoriteTracks))
    }

    private fun converterFromTracksEntity(tracks: List<TrackEntity>): List<Track> {
        return tracks.map { track -> trackDbConvertor.map(track, isFavorite = true) }
    }
}
