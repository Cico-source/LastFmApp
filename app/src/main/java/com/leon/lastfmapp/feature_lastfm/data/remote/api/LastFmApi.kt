package com.leon.lastfmapp.feature_lastfm.data.remote.api

import com.leon.lastfmapp.feature_lastfm.data.remote.responses.ArtistInfoResponse
import com.leon.lastfmapp.feature_lastfm.data.remote.responses.ArtistTopTracksResponse
import com.leon.lastfmapp.feature_lastfm.data.remote.responses.TopArtistsResponse
import com.leon.lastfmapp.feature_lastfm.data.remote.responses.TopTracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi
{
    
    @GET("2.0/?method=chart.gettoptracks")
    suspend fun getTopTracks(): Response<TopTracksResponse>
    
    @GET("2.0/?method=chart.gettopartists")
    suspend fun getTopArtists(): Response<TopArtistsResponse>
    
    @GET("2.0/?method=artist.getinfo")
    suspend fun getArtistInfo(
        @Query("artist") artist: String,
    ): Response<ArtistInfoResponse>
    
    @GET("2.0/?method=artist.gettoptracks")
    suspend fun getArtistTopTracks(
        @Query("artist") artist: String,
    ): Response<ArtistTopTracksResponse>
    
    
    companion object
    {
        const val BASE_URL = "https://ws.audioscrobbler.com/"
        const val API_KEY = ""
    }
    
}