package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_search


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Artistmatches

data class Artistmatches(
    @SerializedName("artist")
    val artist: List<Artist>
)
{
    
    fun toArtistmatches(): Artistmatches
    {
        return Artistmatches(
            artist = artist.map { it.toArtist() }
        )
    }
    
}