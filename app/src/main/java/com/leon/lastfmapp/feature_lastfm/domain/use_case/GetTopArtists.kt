package com.leon.lastfmapp.feature_lastfm.domain.use_case

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository

class GetTopArtists(private val repository: LastFmRepository)
{
    suspend operator fun invoke(): Resource<TopArtists>
    {
        return repository.getTopArtists()
    }
}