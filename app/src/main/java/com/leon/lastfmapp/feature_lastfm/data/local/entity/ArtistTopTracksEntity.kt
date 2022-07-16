package com.leon.lastfmapp.feature_lastfm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.ArtistTopTracks
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.TopTracks

@Entity
class ArtistTopTracksEntity(
    @PrimaryKey val id: Int? = null,
    val toptracks: TopTracks,
    var name: String,
    val date: Long = System.currentTimeMillis(),
)
{
    
    fun toArtistTopTracks(): ArtistTopTracks
    {
        return ArtistTopTracks(
            toptracks = toptracks
        )
    }
}