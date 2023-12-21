package com.example.playlistmaker.search.domain.interactor

import com.example.playlistmaker.search.domain.models.Track

interface GetTracksInteractor {

    fun getTracks (text: String, consumer: GetTrackConsumer)

    interface GetTrackConsumer {
        fun consume(tracks: List<Track>)
    }
}