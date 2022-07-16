package com.leon.lastfmapp.feature_lastfm.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.data.local.entity.ArtistInfoEntity
import com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info.Artist
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.ArtistInfo

data class ArtistInfoResponse(
    @SerializedName("artist")
    val artist: Artist,
)
{
    
    fun toArtistInfo(): ArtistInfo
    {
        return ArtistInfo(
            artist = artist.toArtist()
        )
    }
    
    fun toArtistInfoEntity(): ArtistInfoEntity
    {
        return ArtistInfoEntity(
            artist = artist.toArtist()
        )
    }
    
}