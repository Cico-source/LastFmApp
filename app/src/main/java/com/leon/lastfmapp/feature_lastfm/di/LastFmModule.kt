package com.leon.lastfmapp.feature_lastfm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.leon.lastfmapp.common.util.DispatcherProvider
import com.leon.lastfmapp.feature_lastfm.data.local.Converters
import com.leon.lastfmapp.feature_lastfm.data.local.LastFmDatabase
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi.Companion.API_KEY
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi.Companion.BASE_URL
import com.leon.lastfmapp.feature_lastfm.data.repository.LastFmRepositoryImpl
import com.leon.lastfmapp.feature_lastfm.data.util.GsonParser
import com.leon.lastfmapp.feature_lastfm.domain.repository.LastFmRepository
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetArtistInfo
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetArtistTopTracks
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetArtistsBySearch
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetTopArtists
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetTopTracks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenWeatherModule
{
    
    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ) = context
    
    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider
    {
        return object : DispatcherProvider
        {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
        }
    }
    
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient
    {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("format", "json")
                    .addQueryParameter("limit", "10")
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    
    @Singleton
    @Provides
    fun provideLastFmApi(okHttpClient: OkHttpClient): LastFmApi
    {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(LastFmApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideTopTracksDatabase(app: Application): LastFmDatabase
    {
        return Room.databaseBuilder(
            app, LastFmDatabase::class.java, "lastfm_db"
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }
    
    
    @Singleton
    @Provides
    fun provideLastFmRepository(
        db: LastFmDatabase,
        openWeatherApi: LastFmApi,
        @ApplicationContext context: Context
    ): LastFmRepository = LastFmRepositoryImpl(
        openWeatherApi,
        context,
        db
    )
    
    @Provides
    @Singleton
    fun provideGetTopTracksUseCase(repository: LastFmRepository): GetTopTracks
    {
        return GetTopTracks(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetTopArtistsUseCase(repository: LastFmRepository): GetTopArtists
    {
        return GetTopArtists(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetArtistInfoUseCase(repository: LastFmRepository): GetArtistInfo
    {
        return GetArtistInfo(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetArtistTopTracksUseCase(repository: LastFmRepository): GetArtistTopTracks
    {
        return GetArtistTopTracks(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetArtistsBySearchUseCase(repository: LastFmRepository): GetArtistsBySearch
    {
        return GetArtistsBySearch(repository)
    }
    
}