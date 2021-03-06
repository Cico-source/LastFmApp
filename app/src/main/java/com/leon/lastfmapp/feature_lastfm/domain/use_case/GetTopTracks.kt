package com.leon.lastfmapp.feature_lastfm.domain.use_case

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository

class GetTopTracks(private val repository: LastFmRepository)
{
    suspend operator fun invoke(cacheDuration: Int): Resource<TopTracks>
    {
        return repository.getTopTracks(cacheDuration)
    }
}