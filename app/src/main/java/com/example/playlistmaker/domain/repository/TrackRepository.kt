package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.Track

interface TrackRepository {
    fun getTracks(expression: String): List<Track>
}