package com.leon.lastfmapp.feature_lastfm.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_top_tracks.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.ArtistTopTracks

data class ArtistTopTracksResponse(
    @SerializedName("toptracks")
    val toptracks: TopTracks,
)
{
    
    fun toArtistTopTracks(): ArtistTopTracks
    {
        return ArtistTopTracks(
            toptracks = toptracks.toTopTracks()
        )
    }
    
}