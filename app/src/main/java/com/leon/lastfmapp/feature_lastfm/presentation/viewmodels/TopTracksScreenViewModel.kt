package com.leon.lastfmapp.feature_lastfm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.lastfmapp.common.util.Constants
import com.leon.lastfmapp.common.util.DispatcherProvider
import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.top_tracks.TopTracks
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetTopTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopTracksScreenViewModel @Inject constructor(
    // USE CASES
    private val getTopTracks: GetTopTracks,
    
    private val dispatchers: DispatcherProvider,
    // CACHING
    // private val dao: WeatherDetailsDao
) : ViewModel()
{
    
    sealed class SetupEvent
    {
        
        data class GetTopTracksEvent(val topTracks: TopTracks) : SetupEvent()
        data class GetTopTracksErrorEvent(val error: String) : SetupEvent()
        
        object LoadingEvent : SetupEvent()
        object EmptyEvent : SetupEvent()
    }
    
    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent
    
    private val _screen = MutableStateFlow<SetupEvent>(SetupEvent.EmptyEvent)
    val screen: StateFlow<SetupEvent> = _screen
    
    fun getTopTracks()
    {
        _screen.value = SetupEvent.LoadingEvent
        
        viewModelScope.launch(dispatchers.main) {
            
            val topTracks = getTopTracks(Constants.CACHE_DURATION_MINUTES)
            
            if (topTracks is Resource.Success)
            {
                _screen.value = SetupEvent.GetTopTracksEvent(topTracks.data ?: return@launch)
            }
            else
            {
                _screen.emit(SetupEvent.EmptyEvent)
                _setupEvent.emit(SetupEvent.GetTopTracksErrorEvent(topTracks.message ?: return@launch))
            }
        }
    }
    
}