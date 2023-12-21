package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.search.domain.models.Track

interface TrackRepository {
    fun getTracks(expression: String): List<Track>
}