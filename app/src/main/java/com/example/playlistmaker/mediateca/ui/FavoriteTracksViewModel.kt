package com.example.playlistmaker.mediateca.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.db.domain.FavoritesTracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class FavoriteTracksViewModel(
    private val favoritesTracksInteractor: FavoritesTracksInteractor
) : ViewModel() {

    private var getDbFavoriteTracks = MutableLiveData<List<Track>>()


     fun getFavoritesTracksFromDb(): LiveData<List<Track>> = getDbFavoriteTracks


    fun getFavoritesTracks() {
        viewModelScope.launch {
            favoritesTracksInteractor
                .getFavoritesTracks()
                .collect{getDbFavoriteTracks.postValue(it)}
        }
    }
}