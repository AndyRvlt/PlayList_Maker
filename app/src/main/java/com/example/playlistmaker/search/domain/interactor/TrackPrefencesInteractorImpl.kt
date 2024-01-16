package com.example.playlistmaker.search.domain.interactor


import com.example.playlistmaker.search.domain.models.TrackDataHandler
import com.example.playlistmaker.search.domain.repository.TrackPreferencesRepository

class TrackPrefencesInteractorImpl(
    private val trackPreferencesRepository: TrackPreferencesRepository
) : TrackPreferencesInteractor {


    override fun cleanHistory() {
        trackPreferencesRepository.cleanHistory()
    }

    override fun getTrackPreferences(): TrackDataHandler {
        return trackPreferencesRepository.getTrackPreferences()
    }

    override fun write(trackDataHandler: TrackDataHandler) {
        trackPreferencesRepository.write(trackDataHandler)
    }
}
