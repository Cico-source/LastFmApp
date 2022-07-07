package com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_artists


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Artist

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: String,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("image")
    val image: List<Image>,
)
{
    
    fun toArtist(): Artist
    {
        return Artist(
            name = name,
            playcount = playcount,
            listeners = listeners,
            image = image.map { it.toImage()},
        )
    }
    
}