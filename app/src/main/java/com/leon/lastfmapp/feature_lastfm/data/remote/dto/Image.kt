package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    val text: String,
    @SerializedName("size")
    val size: String
)