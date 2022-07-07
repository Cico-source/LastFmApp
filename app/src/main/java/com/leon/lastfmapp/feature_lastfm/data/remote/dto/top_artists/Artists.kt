package com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_artists


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Artists

data class Artists(
    @SerializedName("artist")
    val artist: List<Artist>
)
{
    
    fun toArtists(): Artists
    {
        return Artists(
            artist = artist.map { it.toArtist() }
        )
    }
    
}