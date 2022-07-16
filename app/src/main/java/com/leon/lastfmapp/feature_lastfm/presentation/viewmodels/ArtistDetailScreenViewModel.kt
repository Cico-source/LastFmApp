package com.leon.lastfmapp.feature_lastfm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.lastfmapp.common.util.Constants
import com.leon.lastfmapp.common.util.DispatcherProvider
import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_info.ArtistInfo
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_top_tracks.ArtistTopTracks
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetArtistInfo
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetArtistTopTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailScreenViewModel @Inject constructor(
    // USE CASES
    private val getArtistInfo: GetArtistInfo,
    private val getArtistTopTracks: GetArtistTopTracks,
    private val dispatchers: DispatcherProvider,
    // CACHING
    // private val dao: WeatherDetailsDao
) : ViewModel()
{
    
    sealed class SetupEvent
    {
        
        data class GetArtistDetailEvent(val artistDetail: Pair<ArtistInfo, ArtistTopTracks>) : SetupEvent()
        data class GetArtistDetailErrorEvent(val error: String) : SetupEvent()
        
        object LoadingEvent : SetupEvent()
        object EmptyEvent : SetupEvent()
    }
    
    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent
    
    private val _screen = MutableStateFlow<SetupEvent>(SetupEvent.EmptyEvent)
    val screen: StateFlow<SetupEvent> = _screen
    
    fun getArtistDetail(artistName: String)
    {
        _screen.value = SetupEvent.LoadingEvent
        
        viewModelScope.launch(dispatchers.main) {
            
            val artistInfo = getArtistInfo(artistName, Constants.CACHE_DURATION_MINUTES)
            
            if (artistInfo is Resource.Error)
            {
                _screen.emit(SetupEvent.EmptyEvent)
                _setupEvent.emit(SetupEvent.GetArtistDetailErrorEvent(artistInfo.message ?: return@launch))
                return@launch
            }
    
            val artistTopTracks = getArtistTopTracks(artistName, Constants.CACHE_DURATION_MINUTES)
            
            if (artistTopTracks is Resource.Error)
            {
                _screen.emit(SetupEvent.EmptyEvent)
                _setupEvent.emit(SetupEvent.GetArtistDetailErrorEvent(artistInfo.message ?: return@launch))
                return@launch
            }
    
            _screen.value = SetupEvent.GetArtistDetailEvent(
                Pair(artistInfo.data ?: return@launch, artistTopTracks.data ?: return@launch)
            )
            
        }
    }
}