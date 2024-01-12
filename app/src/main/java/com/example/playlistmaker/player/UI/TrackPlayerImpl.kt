package com.example.playlistmaker.player.UI

import com.example.playlistmaker.Creator
import com.example.playlistmaker.search.domain.models.Track


class TrackPlayerImpl : TrackPlayer {



    val mediaPlayer = Creator.createPlayer()

    private var playerState = AudioPlayerActivity.STATE_DEFAULT

    override fun preparePlayer(track: Track) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = AudioPlayerActivity.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = AudioPlayerActivity.STATE_PREPARED
        }
    }

    override fun trackPlay(track: Track, trackStatusObserver: TrackPlayer.TrackStatusObserver) {
        trackStatusObserver.onProgress(mediaPlayer.currentPosition.toFloat())

    }

    private fun startPlay() {
        mediaPlayer.start()
        playerState = AudioPlayerActivity.STATE_PLAYING
    }

    private fun pausePlay() {
        mediaPlayer.pause()
        playerState = AudioPlayerActivity.STATE_PAUSED
    }

    override fun playbackControl() {
        when (playerState) {
            AudioPlayerActivity.STATE_PLAYING -> {
                pausePlay()
            }

            AudioPlayerActivity.STATE_PREPARED, AudioPlayerActivity.STATE_PAUSED -> {
                startPlay()
            }
        }
    }
}