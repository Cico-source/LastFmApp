package com.leon.lastfmapp.feature_lastfm.domain.repository

import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.ArtistInfo
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.ArtistSearch
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.ArtistTopTracks
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks

interface LastFmRepository
{
    suspend fun getTopTracks(cacheDuration: Int): Resource<TopTracks>
    
    suspend fun getTopArtists(): Resource<TopArtists>
    
    suspend fun getArtistInfo(artistName: String): Resource<ArtistInfo>
    
    suspend fun getArtistTopTracks(artistName: String): Resource<ArtistTopTracks>
    
    suspend fun getArtistsBySearch(artistName: String): Resource<ArtistSearch>
}