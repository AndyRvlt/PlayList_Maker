package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

    fun getTracks(expression: String): Flow<List<Track>>
}