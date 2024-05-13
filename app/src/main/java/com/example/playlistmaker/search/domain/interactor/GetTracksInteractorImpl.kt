package com.example.playlistmaker.search.domain.interactor

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class GetTracksInteractorImpl(
    private val trackRepository: TrackRepository
) : GetTracksInteractor {

    override fun getTracks(text: String): Flow<List<Track>> {
        return trackRepository.getTracks(text)
    }
}
