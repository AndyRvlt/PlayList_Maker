package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.dto.TrackRequest
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.response.TrackResponse
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.repository.TrackRepository

class TrackRepositoryImpl : TrackRepository {
    private val networkClient: NetworkClient = RetrofitNetworkClient()

    override fun getTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackRequest(expression))

        if (response.resultCode == 200) {
            return (response as TrackResponse).results.map {
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
                    previewUrl = it.previewUrl
                )
            }
        } else {
            return emptyList()
        }
    }
}
