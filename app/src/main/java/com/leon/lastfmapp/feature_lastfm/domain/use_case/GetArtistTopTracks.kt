package com.leon.lastfmapp.feature_lastfm.domain.use_case

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.ArtistTopTracks
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository

class GetArtistTopTracks(private val repository: LastFmRepository)
{
    
    suspend operator fun invoke(artistName: String): Resource<ArtistTopTracks>
    {
        return repository.getArtistTopTracks(artistName)
    }
    
}