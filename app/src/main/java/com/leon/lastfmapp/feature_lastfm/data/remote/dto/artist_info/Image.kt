package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Image

data class Image(
    @SerializedName("#text")
    val text: String
)
{
    
    fun toImage(): Image
    {
        return Image(
            text = text
        )
    }
    
}