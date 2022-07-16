package com.leon.lastfmapp.feature_lastfm.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.data.local.entity.TopTracksEntity
import com.leon.lastfmapp.feature_lastfm.data.remote.dto.top_tracks.Tracks
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks

data class TopTracksResponse(
    @SerializedName("tracks")
    val tracks: Tracks,
)
{
    
    fun toTopTracks(): TopTracks
    {
        return TopTracks(
            tracks = tracks.toTracks()
        )
    }
    
    fun toTopTracksEntity(): TopTracksEntity
    {
        return TopTracksEntity(
            tracks = tracks.toTracks()
        )
    }
    
}