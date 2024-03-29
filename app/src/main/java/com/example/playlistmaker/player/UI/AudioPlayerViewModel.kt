package com.example.playlistmaker.player.UI


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.search.domain.models.Track

class AudioPlayerViewModel(
    private val trackPlayer: TrackPlayer
) : ViewModel() {

    private var playStatusLiveData = MutableLiveData(PlayStatus(false,0f))


    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData



    fun play(track: Track) {
        trackPlayer.trackPlay(track,
            trackStatusObserver = object : TrackPlayer.TrackStatusObserver {
                override fun onProgress(progress: Float) {
                    playStatusLiveData.postValue(playStatusLiveData.value?.copy(onPlayProgressStatus = progress))

                }
            }
        )
    }

    fun getPreparePlayer(track: Track) {

        trackPlayer.preparePlayer(track)
    }

    fun playbackControl() {
        trackPlayer.playbackControl()
    }
}

