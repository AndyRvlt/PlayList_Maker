package com.example.playlistmaker.search.data.network


import com.example.playlistmaker.search.data.response.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {
    @GET("search?entity=song")

    suspend fun search(@Query("term") text: String) : TrackResponse

}