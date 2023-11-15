package com.example.playlistmaker.data.response

import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.dto.TrackDto


class TrackResponse(
    val results: List<TrackDto>
): Response()
//я тут поменял трак на трекДТО


