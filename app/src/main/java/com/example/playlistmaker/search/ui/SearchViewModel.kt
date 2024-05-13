package com.example.playlistmaker.search.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractor
import com.example.playlistmaker.search.domain.interactor.TrackPreferencesInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksInteractor: GetTracksInteractor,
    private val trackPrefencesInteractor: TrackPreferencesInteractor

) : ViewModel() {

    private var selectedTrack: MutableLiveData<Track?> = MutableLiveData(null)
    private var loadingLiveData: MutableLiveData<List<Track>> = MutableLiveData(emptyList())
    private var initTracksLiveData = MutableLiveData(listOf<Track>())
    private var updateLoadedTrack: MutableLiveData<Track?> = MutableLiveData(null)

    fun getTracksLiveData(): LiveData<List<Track>> = loadingLiveData
    fun getInitTracksLiveData(): LiveData<List<Track>> = initTracksLiveData
    fun getSelectedTrackLiveData(): LiveData<Track?> = selectedTrack
    fun getUpdateLoadedTrackLiveData(): LiveData<Track?> = updateLoadedTrack

    fun cleanHistory() {
        trackPrefencesInteractor.cleanHistory()
    }

    fun init() {
        initTracksLiveData.postValue(trackPrefencesInteractor.getTrackPreferences().tracks)
        updateLoadedLiveData()

    }

    private fun updateLoadedLiveData() {
        loadingLiveData.value?.let {
            selectedTrack.value?.let { selectedTrack ->
                val prefTrack = trackPrefencesInteractor.getTrackPreferences().tracks.find {
                    it.trackId == selectedTrack.trackId
                }
                if (prefTrack != null && it.contains(prefTrack)) {
                    updateLoadedTrack.postValue(prefTrack)
                }
            }
        }
    }

    fun getTracks(searchText: String) {
        if (searchText.isNotEmpty()) {
            viewModelScope.launch {
                tracksInteractor
                    .getTracks(searchText)
                    .collect {
                        loadingLiveData.postValue(it)
                    }
            }
        }
    }


    fun setSelectedTrack(track: Track) {
        selectedTrack.postValue(track)
        saveTrack(track)


    }

    private fun saveTrack(track: Track) {
        val trackDataHandler = trackPrefencesInteractor.getTrackPreferences()

        val foundTrack = trackDataHandler.tracks.find { it.trackId == track.trackId }

        if (trackDataHandler.tracks.size < MAX_TRACKS_HISTORY_LIST) {
            if (foundTrack == null) {
                trackDataHandler.tracks.add(0, track)
            } else {
                val index = trackDataHandler.tracks.indexOf(track)
                if (index != -1) {
                    trackDataHandler.tracks.apply {
                        removeAt(indexOf(track))
                        add(0, track)
                    }
                }

            }
        } else {
            trackDataHandler.tracks.removeLast()
            saveTrack(track)
        }

        trackPrefencesInteractor.write(trackDataHandler)
    }


}