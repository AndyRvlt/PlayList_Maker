package com.example.playlistmaker.search.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractor
import com.example.playlistmaker.search.domain.interactor.GetTracksInteractorImpl
import com.example.playlistmaker.search.domain.interactor.TrackPrefencesInteractorImpl
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


    fun cleanHistory(context: Context) {
        trackPrefencesInteractor.cleanHistory(context)

    }


    fun getTracksPref(context: Context, track: Track) {
        getPrefTracks.postValue(trackPrefencesInteractor.getTrackPreferences(context) to track)
    }


    fun init(context: Context) {
        initTracksLiveData.postValue(trackPrefencesInteractor.getTrackPreferences(context).tracks)

    }

    fun writeTracks(context: Context, trackDataHandler: TrackDataHandler) {
        trackPrefencesInteractor.write(context, trackDataHandler)
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

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    return SearchViewModel(
                        GetTracksInteractorImpl(),
                        TrackPrefencesInteractorImpl()
                    ) as T
                }
            }
    }

}