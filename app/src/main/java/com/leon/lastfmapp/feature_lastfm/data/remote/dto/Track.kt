package com.leon.lastfmapp.feature_lastfm.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("playcount")
    val playcount: String,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("mbid")
    val mbid: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("streamable")
    val streamable: Streamable,
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("image")
    val image: List<Image>
)