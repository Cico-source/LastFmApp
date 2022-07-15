package com.leon.lastfmapp.feature_lastfm.domain.use_case

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.ArtistSearch
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository

class GetArtistsBySearch(private val repository: LastFmRepository)
{
    
    suspend operator fun invoke(artistName: String): Resource<ArtistSearch>
    {
        return repository.getArtistsBySearch(artistName)
    }
    
}