package com.example.playlistmaker.db.data


import com.example.playlistmaker.db.TracksDatabase
import com.example.playlistmaker.db.data.entity.TrackEntity
import com.example.playlistmaker.db.domain.FavoritesTracksRepository
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesTracksRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackDbConvertor: TrackDbConvertor,
    private val trackPrefRepository: TrackPreferencesRepository
) : FavoritesTracksRepository {

    override fun addFavoriteTrack(track: Track) {
        val trackEntity = trackDbConvertor.map(track)
        tracksDatabase.trackDao().insertFavoriteTracks(trackEntity)
        updatePrefTrack(track)
    }

    override fun deleteFavoriteTrack(track: Track) {
        val trackEntity = trackDbConvertor.map(track)
        tracksDatabase.trackDao().deleteFavoriteTrack(trackEntity)
        updatePrefTrack(track)
    }

    override suspend fun getFavoritesTracks(): Flow<List<Track>> = flow {
        val favoriteTracks = tracksDatabase.trackDao().getTracksEntity().reversed()
        emit(converterFromTracksEntity(favoriteTracks))
    }

    private fun converterFromTracksEntity(tracks: List<TrackEntity>): List<Track> {
        return tracks.map { track -> trackDbConvertor.map(track, isFavorite = true) }
    }

    private fun updatePrefTrack(track: Track) {
        val trackData = trackPrefRepository.getTrackPreferences()
        if (trackData.tracks.toHashSet().contains(track)) {
            val index = trackData.tracks.indexOf(track)
            trackData.tracks[index] = track
            trackPrefRepository.write(trackData)
        }
    }
}
