package com.example.playlistmaker.domain.interactor

import com.example.playlistmaker.data.repository.TrackRepositoryImpl
import com.example.playlistmaker.domain.repository.TrackRepository
import java.util.concurrent.Executors

class GetTracksInteractorImpl : GetTracksInteractor {
    val trackRepositoryImpl = TrackRepositoryImpl()

    private val executor = Executors.newCachedThreadPool()

    override fun getTracks(text: String, consumer: GetTracksInteractor.GetTrackConsumer) {
        executor.execute {
            consumer.consume(trackRepositoryImpl.getTracks(text))
        }
    }
}