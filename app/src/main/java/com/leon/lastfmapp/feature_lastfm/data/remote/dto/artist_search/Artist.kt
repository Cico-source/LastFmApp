package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_search


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Artist

data class Artist(
    @SerializedName("name")
    val name: String
)
{
    fun toArtist(): Artist
    {
        return Artist(
            name = name
        )
    }
}