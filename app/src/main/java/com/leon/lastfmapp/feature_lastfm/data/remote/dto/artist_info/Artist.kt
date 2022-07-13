package com.leon.lastfmapp.feature_lastfm.data.remote.dto.artist_info


import com.google.gson.annotations.SerializedName
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Artist

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: List<Image>,
    @SerializedName("stats")
    val stats: Stats,
    @SerializedName("tags")
    val tags: Tags,
    @SerializedName("bio")
    val bio: Bio,
)
{
    
    fun toArtist(): Artist
    {
        return Artist(
            name = name,
            image = image.map { it.toImage() },
            stats = stats.toStats(),
            tags = tags.toTags(),
            bio = bio.toBio()
        )
    }
    
}