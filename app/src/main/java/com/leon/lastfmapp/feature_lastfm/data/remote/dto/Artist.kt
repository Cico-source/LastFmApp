package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("url")
    val url: String
)