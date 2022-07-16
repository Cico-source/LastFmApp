package com.leon.lastfmapp.feature_lastfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leon.lastfmapp.feature_lastfm.data.local.entity.ArtistInfoEntity

@Dao
interface ArtistInfoDao
{
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtistInfo(artistInfo: ArtistInfoEntity)
    
    @Query("DELETE FROM artistinfoentity WHERE name = :name")
    suspend fun deleteArtistInfo(name:String)
    
    @Query("SELECT * FROM artistinfoentity WHERE name = :name")
    suspend fun getArtistInfo(name: String): ArtistInfoEntity?
    
}