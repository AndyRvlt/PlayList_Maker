package com.example.playlistmaker.mediateca.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.db.domain.PlaylistInteractor
import com.example.playlistmaker.db.domain.models.PlayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private var getDbPlaylist = MutableLiveData<List<PlayList>>()


    fun getPlaylistsFromDb(): LiveData<List<PlayList>> = getDbPlaylist




    fun getPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.getPlaylists().collect { getDbPlaylist.postValue(it) }
        }
    }


//    init {
//        getDbPlaylist.postValue(listOf(
//            PlayList(id = 0, name = "adsfas", description = "dsfsdfsdf", imageUri = null, trackCount = 2),
//                    PlayList(id = 1, name = "eeeees", description = "eeedf", imageUri = null, trackCount = 2),
//                    PlayList(id = 2, name = "adggggas", description = "dsfgggggggdf", imageUri = null, trackCount = 2)
//        ))
//    }

}