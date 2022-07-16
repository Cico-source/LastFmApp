package com.leon.lastfmapp.feature_lastfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leon.lastfmapp.feature_lastfm.data.local.entity.TopArtistsEntity

@Dao
interface TopArtistsDao
{
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopArtists(topArtists: TopArtistsEntity)
    
    @Query("DELETE FROM topartistsentity")
    suspend fun deleteTopArtists()
    
    @Query("SELECT * FROM topartistsentity")
    suspend fun getTopArtists(): TopArtistsEntity?
    
}