package com.example.playlistmaker.mediateca.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.db.domain.PlaylistInteractor
import com.example.playlistmaker.db.domain.models.PlayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewPlayListViewModel(
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    var playlistState: MutableLiveData<PlayList> = MutableLiveData(null)


    fun getPlaylistState(): LiveData<PlayList> = playlistState

    fun createNewPlaylist(titlePlaylist: String, descriptionPlaylist: String, imagePlaylistUri : String?) {
        viewModelScope.launch(Dispatchers.IO) {
                val playlist =
                    PlayList(
                        id = 0,
                        name = titlePlaylist,
                        description = descriptionPlaylist,
                        imageUri = imagePlaylistUri,
                     //   trackIds = null,
                        trackCount = 0
                    )
                playlistInteractor.addPlaylist(playlist)
                playlistState.postValue(playlist)
        }
    }


}