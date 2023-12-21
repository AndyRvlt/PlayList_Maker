package com.example.playlistmaker.player.UI

import android.media.MediaPlayer
import com.example.playlistmaker.search.domain.models.Track


class MediaPlayer {

   val mediaPlayer = MediaPlayer()

    private var playerState = AudioPlayerActivity.STATE_DEFAULT

    fun preparePlayer(track: Track) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = AudioPlayerActivity.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = AudioPlayerActivity.STATE_PREPARED
        }
    }

    private fun startPlay() {
        mediaPlayer.start()
        playerState = AudioPlayerActivity.STATE_PLAYING
    }

    private fun pausePlay() {
        mediaPlayer.pause()
        playerState = AudioPlayerActivity.STATE_PAUSED
    }

    fun playbackControl() {
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