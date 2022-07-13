package com.leon.lastfmapp.feature_lastfm.domain.model.artist_info

data class Artist(
    val name: String,
    val image: List<Image>,
    val stats: Stats,
    val tags: Tags,
    val bio: Bio
)