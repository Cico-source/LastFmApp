package com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks

data class Track(
    val name: String,
    val playcount: String,
    val listeners: String,
    val image: List<Image>,
)