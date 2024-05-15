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

    private var trackState: MutableLiveData<Track?> = MutableLiveData(null)
    private var audioState: MutableLiveData<PlayStatus> = MutableLiveData(PlayStatus(false, 0f))

    fun getTrackStateLiveData(): LiveData<Track?> = trackState
    fun getAudioStateLiveData(): LiveData<PlayStatus> = audioState

    fun play(track: Track) {
        timerJob = viewModelScope.launch {
            delay(1000L)
            trackPlayer.trackPlay(track,
                trackStatusObserver = object : TrackPlayer.TrackStatusObserver {
                    override fun onProgress(progress: Float) {
                        audioState.postValue(
                            audioState.value?.let {
                                it.copy(onPlayProgressStatus = progress)
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
            trackState.value?.let {
                val track = it.apply { this.isFavorite = !it.isFavorite }

                if (track.isFavorite) {
                    favoritesTracksInteractor.addFavoriteTrack(track)
                } else {
                    favoritesTracksInteractor.deleteFavoriteTrack(track)
                }
                trackState.postValue(track)
            }

        }
    }

    fun saveTrack(track: Track) {
        trackState.postValue(track)
    }

}

