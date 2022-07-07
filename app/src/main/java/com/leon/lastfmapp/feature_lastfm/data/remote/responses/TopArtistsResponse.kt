package com.leon.lastfmapp.feature_lastfm.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_artists.Artists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists

data class TopArtistsResponse(
    @SerializedName("artists")
    val artists: Artists
)
{
    fun toTopArtists(): TopArtists
    {
        return TopArtists(
            artists = artists.toArtists()
        )
    }
    
}