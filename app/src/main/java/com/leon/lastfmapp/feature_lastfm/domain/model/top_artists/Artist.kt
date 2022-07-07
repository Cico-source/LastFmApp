package com.leon.lastfmapp.feature_lastfm.domain.model.top_artists

data class Artist(
    val name: String,
    val playcount: String,
    val listeners: String,
    val image: List<Image>,
)