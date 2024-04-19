package com.example.playlistmaker.search.domain.interactor

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface GetTracksInteractor {

    fun getTracks (text: String): Flow<List<Track>>

}