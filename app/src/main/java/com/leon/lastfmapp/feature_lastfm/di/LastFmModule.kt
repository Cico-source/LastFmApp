package com.leon.lastfmapp.feature_lastfm.di

import android.content.Context
import com.leon.lastfmapp.common.util.DispatcherProvider
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi.Companion.API_KEY
import com.leon.lastfmapp.feature_lastfm.data.remote.api.LastFmApi.Companion.BASE_URL
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
    fun provideOpenWeatherApi(okHttpClient: OkHttpClient): LastFmApi
    {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(LastFmApi::class.java)
    }
    
}