package com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_tracks


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.Image

data class Image(
    @SerializedName("#text")
    val text: String,
)
{
    
    fun toImage(): Image
    {
        return Image(
            text = text,
        )
    }
    
}