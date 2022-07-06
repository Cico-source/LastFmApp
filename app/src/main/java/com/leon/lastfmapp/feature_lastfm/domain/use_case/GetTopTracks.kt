package com.leon.lastfmapp.feature_lastfm.domain.use_case

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository

class GetTopTracks(private val repository: LastFmRepository)
{
    suspend operator fun invoke(): Resource<TopTracks>
    {
        return repository.getTopTracks()
    }
}