package com.leon.lastfmapp.feature_lastfm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leon.lastfmapp.feature_lastfm.data.local.dao.TopArtistsDao
import com.leon.lastfmapp.feature_lastfm.data.local.dao.TopTracksDao
import com.leon.lastfmapp.feature_lastfm.data.local.entity.TopArtistsEntity
import com.leon.lastfmapp.feature_lastfm.data.local.entity.TopTracksEntity


@Database(
	entities = [TopTracksEntity::class, TopArtistsEntity::class],
	version = 1
)
@TypeConverters(Converters::class)
abstract class LastFmDatabase : RoomDatabase()
{
	abstract val topTracksdao: TopTracksDao
	abstract val topArtistsdao: TopArtistsDao
}
