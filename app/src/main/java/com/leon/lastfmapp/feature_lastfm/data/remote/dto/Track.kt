package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.Track

data class Track(
    @SerializedName("name")
    val name: String,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("playcount")
    val playcount: String,
    @SerializedName("image")
    val image: List<Image>,
)
{
    
    fun toTrack(): Track
    {
        return Track(
            name = name,
            artist = artist.toArtist(),
            listeners = listeners,
            playcount = playcount,
            image = image.map { it.toImage() },
        )
    }
    
}