package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("track")
    val track: List<Track>
)