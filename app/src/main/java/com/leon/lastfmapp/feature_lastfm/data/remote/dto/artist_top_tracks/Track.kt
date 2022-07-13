package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_top_tracks


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.Track

data class Track(
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
    
    fun toTrack(): Track
    {
        return Track(
            name = name,
            playcount = playcount,
            listeners = listeners,
            image = image.map { it.toImage() }
            )
    }
    
}