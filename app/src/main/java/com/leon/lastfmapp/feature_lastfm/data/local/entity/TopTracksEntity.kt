package com.leon.lastfmapp.feature_lastfm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.Tracks
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks

@Entity
class TopTracksEntity(
    @PrimaryKey val id: Int? = null,
    val tracks: Tracks,
    val date: Long = System.currentTimeMillis(),
    )
{
    fun toTopTracks(): TopTracks
    {
        return TopTracks(
            tracks = tracks
        )
    }
}