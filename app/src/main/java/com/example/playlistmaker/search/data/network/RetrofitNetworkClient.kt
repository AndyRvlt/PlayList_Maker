package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.search.data.dto.Response
import com.example.playlistmaker.search.data.dto.TrackRequest

class RetrofitNetworkClient(
     private val trackService: TrackApi
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (dto is TrackRequest) {
            val resp = trackService.search(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }

        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}