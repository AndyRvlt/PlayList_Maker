package com.example.playlistmaker.search.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractor
import com.example.playlistmaker.search.domain.interactor.TrackPreferencesInteractor
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.models.TrackDataHandler

class SearchViewModel(
    private val tracksInteractor: GetTracksInteractor,
    private val trackPrefencesInteractor: TrackPreferencesInteractor

) : ViewModel() {

    private var loadingLiveData: MutableLiveData<List<Track>?> = MutableLiveData(null)
    private var initTracksLiveData = MutableLiveData(listOf<Track>())
    private var getPrefTracks: MutableLiveData<Pair<TrackDataHandler, Track?>> = MutableLiveData(
        TrackDataHandler(
            mutableListOf()
        ) to null
    )

    fun getTracksLiveData(): LiveData<List<Track>?> = loadingLiveData
    fun getInitTracksLiveData(): LiveData<List<Track>> = initTracksLiveData
    fun getPrefTracksLiveData(): LiveData<Pair<TrackDataHandler, Track?>> = getPrefTracks


    fun cleanHistory() {
        trackPrefencesInteractor.cleanHistory()
    }


    fun getTracksPref(track: Track) {
        getPrefTracks.postValue(trackPrefencesInteractor.getTrackPreferences() to track)
    }


    fun init() {
        initTracksLiveData.postValue(trackPrefencesInteractor.getTrackPreferences().tracks)

    }

    fun writeTracks(trackDataHandler: TrackDataHandler) {
        trackPrefencesInteractor.write(trackDataHandler)
    }

    fun getTracks(searchText: String) {


        if (searchText.isNotEmpty()) {

            tracksInteractor.getTracks(searchText, object : GetTracksInteractor.GetTrackConsumer {
                override fun consume(tracks: List<Track>) {
                    loadingLiveData.postValue(tracks)
                }
            })
        }
    }

}