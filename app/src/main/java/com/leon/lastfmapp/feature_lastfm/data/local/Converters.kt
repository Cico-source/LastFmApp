package com.leon.lastfmapp.feature_lastfm.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.leon.lastfmapp.feature_lastfm.data.util.JsonParser
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