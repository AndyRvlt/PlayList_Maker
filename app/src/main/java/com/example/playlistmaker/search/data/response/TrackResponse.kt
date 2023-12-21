package com.example.playlistmaker.search.data.response

import com.example.playlistmaker.search.data.dto.Response
import com.example.playlistmaker.search.data.dto.TrackDto


class TrackResponse(
    val results: List<TrackDto>
): Response()



