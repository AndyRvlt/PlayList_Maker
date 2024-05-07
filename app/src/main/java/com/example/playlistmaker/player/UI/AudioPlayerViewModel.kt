package com.example.playlistmaker.player.UI


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.db.domain.FavoritesTracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.*


class AudioPlayerViewModel(
    private val trackPlayer: TrackPlayer,
    private val favoritesTracksInteractor: FavoritesTracksInteractor
) : ViewModel() {

    private var timerJob: Job? = null

    private var state =
        MutableLiveData(AudioPlayerState(track = null, playState = PlayStatus(false, 0f)))

    fun getStateLiveData(): LiveData<AudioPlayerState> = state


    fun play(track: Track) {

        timerJob = viewModelScope.launch {
            delay(1000L)
            trackPlayer.trackPlay(track,
                trackStatusObserver = object : TrackPlayer.TrackStatusObserver {
                    override fun onProgress(progress: Float) {
                        state.postValue(
                            state.value?.let {
                                it.copy(
                                    playState = it.playState.copy(onPlayProgressStatus = progress)
                                )
                            }
                        )
                    }
                }
            )
            play(track)
        }

    }

    fun stopPlay() {
        timerJob?.cancel()
        timerJob = null
        trackPlayer.reset()
    }

    fun getPreparePlayer(track: Track) {

        trackPlayer.preparePlayer(track)
    }

    fun playbackControl() {
        trackPlayer.playbackControl()
    }


    fun onFavoriteClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value?.let {
                state.postValue(
                    it.copy(
                        track = it.track?.copy(isFavorite = !it.track.isFavorite)
                    )
                )
                if (it.track != null) {
                    if (!it.track.isFavorite) {
                        favoritesTracksInteractor.addFavoriteTrack(it.track)
                    } else {
                        favoritesTracksInteractor.deleteFavoriteTrack(it.track)
                    }
                }
            }
        }
    }

    fun saveTrack(track: Track) {
        state.postValue(
            state.value?.copy(
                track = track
            )
        )
    }

    data class AudioPlayerState(
        val track: Track?,
        val playState: PlayStatus
    )

}

