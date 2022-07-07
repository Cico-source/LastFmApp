package com.leon.lastfmapp.feature_lastfm.data.repository

import android.content.Context
import com.leon.lastfmapp.R
import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.common.util.checkForInternetConnection
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi
import com.leon.lastfmapp.feature_lastfm.domain.model.top_artists.TopArtists
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LastFmRepositoryImpl @Inject constructor(
    private val lastFmApi: LastFmApi,
    private val context: Context,
    // private val dao: WeatherDetailsDao

) : LastFmRepository
{
    
    override suspend fun getTopTracks(): Resource<TopTracks>
    {
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
            // if (caching)
            // {
            //     // Remove existing cache
            //     dao.deleteWeatherDetails()
            //
            //     // Update with new cache
            //     dao.insertWeatherDetails(body.toWeatherDetailsEntity())
            // }
            
            // return Resource.Success(body.toWeatherDetails())
            
            return Resource.Success(body.toTopTracks())
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
        
    }
    
    override suspend fun getTopArtists(): Resource<TopArtists>
    {
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
            // if (caching)
            // {
            //     // Remove existing cache
            //     dao.deleteWeatherDetails()
            //
            //     // Update with new cache
            //     dao.insertWeatherDetails(body.toWeatherDetailsEntity())
            // }
            
            // return Resource.Success(body.toWeatherDetails())
            
            return Resource.Success(body.toTopArtists())
        }
        else
        {
            return Resource.Error(context.getString(R.string.error_unknown))
        }
        
    }
    
}