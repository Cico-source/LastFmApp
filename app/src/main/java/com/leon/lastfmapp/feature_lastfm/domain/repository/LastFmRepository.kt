package com.leon.lastfmapp.feature_lastfm.domain.repository

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.TopTracks

interface LastFmRepository
{
    suspend fun getTopTracks(): Resource<TopTracks>
}