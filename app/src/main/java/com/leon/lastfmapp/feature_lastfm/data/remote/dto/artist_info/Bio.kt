package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Bio

data class Bio(
    @SerializedName("published")
    val published: String,
    @SerializedName("summary")
    val summary: String
)
{
    
    fun toBio(): Bio
    {
        return Bio(
            published = published,
            summary = summary
        )
    }
    
}