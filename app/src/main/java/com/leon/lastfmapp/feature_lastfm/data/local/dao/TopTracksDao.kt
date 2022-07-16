package com.leon.lastfmapp.feature_lastfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leon.lastfmapp.feature_lastfm.data.local.entity.TopTracksEntity

@Dao
interface TopTracksDao
{
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTopTracks(topTracks: TopTracksEntity)
	
	@Query("DELETE FROM toptracksentity")
	suspend fun deleteTopTracks()
	
	@Query("SELECT * FROM toptracksentity")
	suspend fun getTopTracks(): TopTracksEntity?
	
}