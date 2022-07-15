package com.leon.lastfmapp.feature_lastfm.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_search.Results
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.ArtistSearch

data class ArtistSearchResponse(
    @SerializedName("results")
    val results: Results
)
{
    fun toArtistSearch():ArtistSearch
    {
        return ArtistSearch(
            results = results.toResults()
        )
    }
}