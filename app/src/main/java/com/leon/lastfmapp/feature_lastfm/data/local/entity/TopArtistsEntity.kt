package com.leon.lastfmapp.feature_lastfm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Artists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists

@Entity
class TopArtistsEntity(
    @PrimaryKey val id: Int? = null,
    val artists: Artists,
    val date: Long = System.currentTimeMillis(),
)
{
    
    fun toTopArtists(): TopArtists
    {
        return TopArtists(
            artists = artists
        )
    }
}