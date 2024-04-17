package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.search.data.dto.Response
import com.example.playlistmaker.search.data.dto.TrackRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(
     private val trackService: TrackApi
) : NetworkClient {

    override  suspend fun doRequest(dto: Any): Response {
        if (dto is TrackRequest) {


            return withContext(Dispatchers.IO){
                try {
                    val resp = trackService.search(dto.expression)
                    resp.apply { resultCode = 200 }
                } catch (e:Throwable){
                    Response().apply { resultCode = 500 }
                }
            }

        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}