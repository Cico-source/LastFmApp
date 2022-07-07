package com.leon.lastfmapp.feature_lastfm.domain.repository

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks

interface LastFmRepository
{
    suspend fun getTopTracks(): Resource<TopTracks>
    
    suspend fun getTopArtists(): Resource<TopArtists>
}