package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Streamable(
    @SerializedName("#text")
    val text: String,
    @SerializedName("fulltrack")
    val fulltrack: String
)