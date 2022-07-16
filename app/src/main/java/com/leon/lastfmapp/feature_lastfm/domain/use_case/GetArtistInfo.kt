package com.leon.lastfmapp.feature_lastfm.domain.use_case

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.ArtistInfo
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository

class GetArtistInfo(private val repository: LastFmRepository)
{
    
    suspend operator fun invoke(artistName: String, cacheDuration: Int): Resource<ArtistInfo>
    {
        return repository.getArtistInfo(artistName, cacheDuration)
    }
    
}