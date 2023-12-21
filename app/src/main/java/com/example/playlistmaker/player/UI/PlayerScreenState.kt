package com.example.playlistmaker.player.UI

import com.example.playlistmaker.search.domain.models.Track

sealed class PlayerScreenState {
    object Loading: PlayerScreenState()
    data class Content(
        val track: Track,
    ): PlayerScreenState()
}
