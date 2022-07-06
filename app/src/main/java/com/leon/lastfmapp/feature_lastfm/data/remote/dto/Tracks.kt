package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.Tracks

data class Tracks(
    @SerializedName("track")
    val track: List<Track>,
)
{
    
    fun toTracks(): Tracks
    {
        return Tracks(
            track = track.map { it.toTrack() },
        )
    }
}