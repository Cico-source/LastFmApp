package com.leon.lastfmapp.feature_lastfm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Artist
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.ArtistInfo

@Entity
class ArtistInfoEntity(
    @PrimaryKey val id: Int? = null,
    val artist: Artist,
    val name: String = artist.name,
    val date: Long = System.currentTimeMillis(),
)
{
    
    fun toArtistInfo(): ArtistInfo
    {
        return ArtistInfo(
            artist = artist
        )
    }
}