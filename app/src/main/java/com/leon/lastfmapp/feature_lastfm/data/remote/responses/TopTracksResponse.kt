package com.leon.lastfmapp.feature_lastfm.data.remote.responses


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.data.remote.dto.Tracks

data class TopTracksResponse(
    @SerializedName("tracks")
    val tracks: Tracks
)