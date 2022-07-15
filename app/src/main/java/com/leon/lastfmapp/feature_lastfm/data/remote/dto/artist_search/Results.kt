package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_search


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Results

data class Results(
    @SerializedName("artistmatches")
    val artistmatches: Artistmatches
)
{
    
    fun toResults(): Results
    {
        return Results(
            artistmatches = artistmatches.toArtistmatches()
        )
    }
    
}