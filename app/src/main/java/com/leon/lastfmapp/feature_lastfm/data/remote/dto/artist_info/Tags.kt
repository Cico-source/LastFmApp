package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Tags

data class Tags(
    @SerializedName("tag")
    val tag: List<Tag>
)
{
    
    fun toTags(): Tags
    {
        return Tags(
            tag = tag.map { it.toTag() }
        )
    }
    
}