package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.db.TracksDatabase
import com.example.playlistmaker.search.data.dto.TrackRequest
import com.example.playlistmaker.search.data.network.NetworkClient
import com.example.playlistmaker.search.data.response.TrackResponse
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val tracksDatabase: TracksDatabase
) : TrackRepository {

    override fun getTracks(expression: String): Flow<List<Track>> = flow {
        val response = networkClient.doRequest(TrackRequest(expression))

        val dbIdTracks = tracksDatabase.trackDao().getTracksIdList()

        if (response.resultCode == 200) {
            val data = (response as TrackResponse).results.map {
                Track(
                    trackName = it.trackName,
                    artistName = it.artistName,
                    trackTimeMillis = it.trackTimeMillis,
                    artworkUrl100 = it.artworkUrl100,
                    trackId = it.trackId,
                    collectionName = it.collectionName,
                    releaseDate = it.releaseDate,
                    primaryGenreName = it.primaryGenreName,
                    country = it.country,
                    previewUrl = it.previewUrl,
//                    isFavorite = dbIdTracks.find { trackId -> it.trackId == trackId } != null
                ).apply { this.isFavorite = dbIdTracks.find { trackId -> it.trackId == trackId } != null }
            }

            emit(data)


        } else {
            emit(emptyList())
        }

    }

}