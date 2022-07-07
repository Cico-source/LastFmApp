package com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_tracks


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.Artist

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("url")
    val url: String,
)
{
    fun toArtist(): Artist
    {
        return Artist(
            name = name,
            mbid = mbid,
            url = url,
        )
    }
}