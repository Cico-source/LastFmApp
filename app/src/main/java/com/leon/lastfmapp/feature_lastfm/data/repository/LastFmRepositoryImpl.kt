package com.leon.lastfmapp.feature_lastfm.data.repository

import android.content.Context
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.common.util.checkForInternetConnection
import com.leon.lastfmapp.feature_lastfm.data.local.LastFmDatabase
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.ArtistInfo
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.ArtistSearch
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.ArtistTopTracks
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository
import retrofit2.HttpException
import java.io.IOException
import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

class LastFmRepositoryImpl @Inject constructor(
    private val lastFmApi: LastFmApi,
    private val context: Context,
    private val db: LastFmDatabase

) : LastFmRepository
{
    
    override suspend fun getTopTracks(cacheDuration: Int): Resource<TopTracks>
    {
        val topTracksEntity = db.topTracksDao.getTopTracks()
    
        if (cacheDuration > 0)
        {
            topTracksEntity?.let {
            
                val timestamp = Timestamp(it.date)
                val calBaza = Calendar.getInstance()
                calBaza.time = timestamp
            
                val calTrenutno = Calendar.getInstance()
                calTrenutno.add(Calendar.MINUTE, -cacheDuration)
            
                // If cached data is not older than cacheDuration
                if (calTrenutno.time.before(calBaza.time))
                {
                    // Load cache
                    return Resource.Success(topTracksEntity.toTopTracks())
                }
                else
                {
                    // Delete old cache
                    db.topTracksDao.deleteTopTracks()
                }
            }
        
        }
        
        if (!context.checkForInternetConnection())
        {
            return Resource.Error(context.getString(R.string.error_internet_turned_off))
        }
        
        val response = try
        {
            lastFmApi.getTopTracks()
        }
        catch (e: HttpException)
        {
            return Resource.Error(context.getString(R.string.error_http))
        }
        catch (e: IOException)
        {
            return Resource.Error(context.getString(R.string.check_internet_connection))
        }
        
        val body = response.body()
        
        if (response.isSuccessful && body != null)
        {
            
            // Update with new cache
            db.topTracksDao.insertTopTracks(body.toTopTracksEntity())
            
            return Resource.Success(body.toTopTracks())
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
        
    }
    
    override suspend fun getTopArtists(cacheDuration: Int): Resource<TopArtists>
    {
        val topArtistsEntity = db.topArtistsDao.getTopArtists()
    
        if (cacheDuration > 0)
        {
            topArtistsEntity?.let {
            
                val timestamp = Timestamp(it.date)
                val calBaza = Calendar.getInstance()
                calBaza.time = timestamp
            
                val calTrenutno = Calendar.getInstance()
                calTrenutno.add(Calendar.MINUTE, -cacheDuration)
            
                // If cached data is not older than cacheDuration
                if (calTrenutno.time.before(calBaza.time))
                {
                    // Load cache
                    return Resource.Success(topArtistsEntity.toTopArtists())
                }
                else
                {
                    // Delete old cache
                    db.topTracksDao.deleteTopTracks()
                }
            }
        
        }
        
        if (!context.checkForInternetConnection())
        {
            return Resource.Error(context.getString(R.string.error_internet_turned_off))
        }
        
        val response = try
        {
            lastFmApi.getTopArtists()
        }
        catch (e: HttpException)
        {
            return Resource.Error(context.getString(R.string.error_http))
        }
        catch (e: IOException)
        {
            return Resource.Error(context.getString(R.string.check_internet_connection))
        }
        
        val body = response.body()
        
        if (response.isSuccessful && body != null)
        {
            // Update with new cache
            db.topArtistsDao.insertTopArtists(body.toTopArtistsEntity())
            
            return Resource.Success(body.toTopArtists())
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
        
    }
    
    override suspend fun getArtistInfo(artistName: String, cacheDuration: Int): Resource<ArtistInfo>
    {
        val artistInfoEntity = db.artistInfoDao.getArtistInfo(artistName)
    
        if (cacheDuration > 0)
        {
            artistInfoEntity?.let {
            
                val timestamp = Timestamp(it.date)
                val calBaza = Calendar.getInstance()
                calBaza.time = timestamp
            
                val calTrenutno = Calendar.getInstance()
                calTrenutno.add(Calendar.MINUTE, -cacheDuration)
            
                // If cached data is not older than cacheDuration
                if (calTrenutno.time.before(calBaza.time))
                {
                    // Load cache
                    return Resource.Success(artistInfoEntity.toArtistInfo().also {
                        it.artist.bio.summary = it.artist.bio.summary.replace("<a href.*$".toRegex(), "")
    
                        val df2 = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
                        it.artist.stats.playcount = df2.format(it.artist.stats.playcount.toInt())
                        it.artist.stats.listeners = df2.format(it.artist.stats.listeners.toInt())
                    })
                }
                else
                {
                    // Delete old cache
                    db.artistInfoDao.deleteArtistInfo(artistName)
                }
            }
        
        }
        
        if (!context.checkForInternetConnection())
        {
            return Resource.Error(context.getString(R.string.error_internet_turned_off))
        }
    
        val response = try
        {
            lastFmApi.getArtistInfo(artistName)
        }
        catch (e: HttpException)
        {
            return Resource.Error(context.getString(R.string.error_http))
        }
        catch (e: IOException)
        {
            return Resource.Error(context.getString(R.string.check_internet_connection))
        }
    
        val body = response.body()
    
        if (response.isSuccessful && body != null)
        {
            // Update with new cache
            db.artistInfoDao.insertArtistInfo(body.toArtistInfoEntity())
        
            return Resource.Success(body.toArtistInfo()
                .also {
                    it.artist.bio.summary = it.artist.bio.summary.replace("<a href.*$".toRegex(), "")
                    if (it.artist.bio.summary.isBlank())
                    {
                        it.artist.bio.summary = "No description."
                    }
                    
                    
                    
                    val df2 = DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.getDefault()))
                    it.artist.stats.playcount = df2.format(it.artist.stats.playcount.toInt())
                    it.artist.stats.listeners = df2.format(it.artist.stats.listeners.toInt())
                })
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
    }
    
    override suspend fun getArtistTopTracks(artistName: String, cacheDuration: Int): Resource<ArtistTopTracks>
    {
        val artistTopTracksEntity = db.artistTopTracksDao.getArtistTopTracks(artistName)
    
        if (cacheDuration > 0)
        {
            artistTopTracksEntity?.let {
            
                val timestamp = Timestamp(it.date)
                val calBaza = Calendar.getInstance()
                calBaza.time = timestamp
            
                val calTrenutno = Calendar.getInstance()
                calTrenutno.add(Calendar.MINUTE, -cacheDuration)
            
                // If cached data is not older than cacheDuration
                if (calTrenutno.time.before(calBaza.time))
                {
                    // Load cache
                    return Resource.Success(artistTopTracksEntity.toArtistTopTracks())
                }
                else
                {
                    // Delete old cache
                    db.artistTopTracksDao.deleteArtistTopTracks(artistName)
                }
            }
        
        }
        
        if (!context.checkForInternetConnection())
        {
            return Resource.Error(context.getString(R.string.error_internet_turned_off))
        }
    
        val response = try
        {
            lastFmApi.getArtistTopTracks(artistName)
        }
        catch (e: HttpException)
        {
            return Resource.Error(context.getString(R.string.error_http))
        }
        catch (e: IOException)
        {
            return Resource.Error(context.getString(R.string.check_internet_connection))
        }
    
        val body = response.body()
    
        if (response.isSuccessful && body != null)
        {
            // Update with new cache
            db.artistTopTracksDao.insertArtistTopTracks(body.toArtistTopTracksEntity(artistName))
        
            return Resource.Success(body.toArtistTopTracks())
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
    }
    
    override suspend fun getArtistsBySearch(artistName: String): Resource<ArtistSearch>
    {
        if (!context.checkForInternetConnection())
        {
            return Resource.Error(context.getString(R.string.error_internet_turned_off))
        }
    
        val response = try
        {
            lastFmApi.getArtistsBySearch(artistName)
        }
        catch (e: HttpException)
        {
            return Resource.Error(context.getString(R.string.error_http))
        }
        catch (e: IOException)
        {
            return Resource.Error(context.getString(R.string.check_internet_connection))
        }
    
        val body = response.body()
    
        if (response.isSuccessful && body != null)
        {
            return Resource.Success(body.toArtistSearch())
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
    }
    
}