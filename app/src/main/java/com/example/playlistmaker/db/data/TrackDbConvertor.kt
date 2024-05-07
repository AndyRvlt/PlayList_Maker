package com.example.playlistmaker.db.data

import com.example.playlistmaker.db.data.entity.TrackEntity
import com.example.playlistmaker.search.domain.models.Track

class TrackDbConvertor {

    fun map(track: Track): TrackEntity {
        return TrackEntity(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            trackId = track.trackId,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl
        )
    }
    fun map (track: TrackEntity, isFavorite: Boolean): Track {
        return Track(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            trackId = track.trackId,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = isFavorite
        )
    }
}