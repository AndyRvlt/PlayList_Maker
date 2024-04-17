package com.example.playlistmaker.player.UI


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AudioPlayerViewModel(
    private val trackPlayer: TrackPlayer
) : ViewModel() {

    private var timerJob: Job? = null

    private var playStatusLiveData = MutableLiveData(PlayStatus(false, 0f))


    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData


    fun play(track: Track) {

        timerJob = viewModelScope.launch {
            delay(1000L)
            trackPlayer.trackPlay(track,
                trackStatusObserver = object : TrackPlayer.TrackStatusObserver {
                    override fun onProgress(progress: Float) {
                        playStatusLiveData.postValue(
                            playStatusLiveData.value?.copy(
                                onPlayProgressStatus = progress
                            )
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

}

