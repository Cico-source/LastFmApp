package com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_artists


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Image

data class Image(
    @SerializedName("#text")
    val text: String,
    @SerializedName("size")
    val size: String,
)
{
    
    fun toImage(): Image
    {
        return Image(
            text = text,
            size = size
        )
    }
    
}