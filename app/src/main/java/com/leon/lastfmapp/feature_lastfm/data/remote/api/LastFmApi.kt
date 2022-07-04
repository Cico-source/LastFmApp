package com.leon.lastfmapp.feature_lastfm.data.remote.api

import com.leon.lastfmapp.feature_lastfm.data.remote.responses.TopTracksResponse
import retrofit2.Response
import retrofit2.http.GET

interface LastFmApi
{
    
    @GET("2.0/?method=chart.gettoptracks")
    suspend fun getTopTracks(
        // @Query("q") city: String,
    
    ): Response<TopTracksResponse>
    
    
    companion object
    {
        const val BASE_URL = "https://ws.audioscrobbler.com/"
        const val API_KEY = ""
    }
    
}