package com.leon.lastfmapp.feature_lastfm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leon.lastfmapp.feature_lastfm.data.local.entity.TopTracksEntity


@Database(
	entities = [TopTracksEntity::class],
	version = 1
)
@TypeConverters(Converters::class)
abstract class LastFmDatabase : RoomDatabase()
{
	abstract val topTracksdao: TopTracksDao
}
