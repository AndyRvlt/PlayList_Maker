package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.domain.models.Track

interface GetTracksInteractor {

    fun getTracks (text: String, consumer: GetTrackConsumer)

    interface GetTrackConsumer {
        fun consume(tracks: List<Track>)
    }
}