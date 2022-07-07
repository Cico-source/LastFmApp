package com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks

data class Track(
    val name: String,
    val artist: Artist,
    val listeners: String,
    val playcount: String,
    val image: List<Image>,
)
