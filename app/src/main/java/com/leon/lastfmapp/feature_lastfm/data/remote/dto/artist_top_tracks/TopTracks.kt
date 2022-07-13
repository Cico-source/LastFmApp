package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_top_tracks


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.TopTracks

data class TopTracks(
    @SerializedName("track")
    val track: List<Track>
)
{
    
    fun toTopTracks(): TopTracks
    {
        return TopTracks(
            track = track.map { it.toTrack() }
        )
    }
    
}