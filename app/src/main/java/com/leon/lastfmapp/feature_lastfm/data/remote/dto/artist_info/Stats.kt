package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Stats

data class Stats(
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("playcount")
    val playcount: String
)
{
    
    fun toStats():Stats
    {
        return Stats(
            listeners = listeners,
            playcount = playcount
        )
    }
    
}