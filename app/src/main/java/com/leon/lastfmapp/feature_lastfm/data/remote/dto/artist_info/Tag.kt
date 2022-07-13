package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Tag

data class Tag(
    @SerializedName("name")
    val name: String
)
{
    
    fun toTag(): Tag
    {
        return Tag(
            name = name
        )
    }
    
}