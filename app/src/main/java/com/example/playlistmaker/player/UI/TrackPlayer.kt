package com.example.playlistmaker.player.UI

import com.example.playlistmaker.search.domain.models.Track

interface TrackPlayer {

    fun preparePlayer(track: Track)

    fun trackPlay(track: Track, trackStatusObserver: TrackStatusObserver)

    fun playbackControl()

    fun reset()

    interface TrackStatusObserver {

        fun onProgress (progress: Float)

    }

}