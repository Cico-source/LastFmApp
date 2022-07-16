package com.leon.lastfmapp.feature_lastfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leon.lastfmapp.feature_lastfm.data.local.entity.ArtistTopTracksEntity

@Dao
interface ArtistTopTracksDao
{
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtistTopTracks(artistTopTracks: ArtistTopTracksEntity)
    
    @Query("DELETE FROM artisttoptracksentity WHERE name = :name")
    suspend fun deleteArtistTopTracks(name: String)
    
    @Query("SELECT * FROM artisttoptracksentity WHERE name = :name")
    suspend fun getArtistTopTracks(name: String): ArtistTopTracksEntity?
    
}