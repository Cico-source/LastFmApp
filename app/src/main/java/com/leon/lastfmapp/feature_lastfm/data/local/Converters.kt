package com.leon.lastfmapp.feature_lastfm.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.leon.lastfmapp.feature_lastfm.data.util.JsonParser
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Artist
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Bio
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Stats
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.Tags
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.Artists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.Tracks

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
)
{
    
    @TypeConverter
    fun fromTracksJson(json: String): Tracks
    {
        return jsonParser.fromJson<Tracks>(
            json,
            object : TypeToken<Tracks>()
            {}.type
        ) ?: Tracks(emptyList())
    }
    
    @TypeConverter
    fun toTracksJson(current: Tracks): String
    {
        return jsonParser.toJson(
            current,
            object : TypeToken<Tracks>()
            {}.type
        ) ?: "{}"
    }
    
    @TypeConverter
    fun fromArtistsJson(json: String): Artists
    {
        return jsonParser.fromJson<Artists>(
            json,
            object : TypeToken<Artists>()
            {}.type
        ) ?: Artists(emptyList())
    }
    
    @TypeConverter
    fun toArtistsJson(current: Artists): String
    {
        return jsonParser.toJson(
            current,
            object : TypeToken<Artists>()
            {}.type
        ) ?: "{}"
    }
    
    @TypeConverter
    fun fromArtistJson(json: String): Artist
    {
        return jsonParser.fromJson<Artist>(
            json,
            object : TypeToken<Artist>()
            {}.type
        ) ?: Artist(
            name = "",
            image = listOf(),
            stats = Stats("", ""),
            Tags(listOf()),
            Bio("", "")
        )
    }
    
    @TypeConverter
    fun toArtistJson(current: Artist): String
    {
        return jsonParser.toJson(
            current,
            object : TypeToken<Artist>()
            {}.type
        ) ?: "{}"
    }
    
    @TypeConverter
    fun fromTopTracksJson(json: String): TopTracks
    {
        return jsonParser.fromJson<TopTracks>(
            json,
            object : TypeToken<TopTracks>()
            {}.type
        ) ?: TopTracks(listOf())
    }
    
    @TypeConverter
    fun toTopTracksJson(current: TopTracks): String
    {
        return jsonParser.toJson(
            current,
            object : TypeToken<TopTracks>()
            {}.type
        ) ?: "{}"
    }
    
    
    
    // @TypeConverter
    // fun fromMeaningsJson(json: String): List<Meaning>
    // {
    //     return jsonParser.fromJson<ArrayList<Meaning>>(
    //         json,
    //         object : TypeToken<ArrayList<Meaning>>()
    //         {}.type
    //     ) ?: emptyList()
    // }
    //
    // @TypeConverter
    // fun toMeaningsJson(meanings: List<Meaning>): String
    // {
    //     return jsonParser.toJson(
    //         meanings,
    //         object : TypeToken<ArrayList<Meaning>>()
    //         {}.type
    //     ) ?: "[]"
    // }
    
}