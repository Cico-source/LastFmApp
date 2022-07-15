package com.leon.lastfmapp.feature_lastfm.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.lastfmapp.common.util.DispatcherProvider
import com.leon.lastfmapp.common.util.Resource
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.ArtistSearch
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Artistmatches
import com.leon.lastfmapp.feature_lastfm.domain.model.artist_search.Results
import com.leon.lastfmapp.feature_lastfm.domain.use_case.GetArtistsBySearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    // USE CASES
    private val getArtistsBySearch: GetArtistsBySearch,
    private val dispatchers: DispatcherProvider,
    // CACHING
    // private val dao: WeatherDetailsDao
) : ViewModel()
{
    
    sealed class SetupEvent
    {
        
        data class GetSearchArtistsEvent(val searchedArtists: ArtistSearch) : SetupEvent()
        data class GetSearchArtistsErrorEvent(val error: String) : SetupEvent()
        
        object LoadingEvent : SetupEvent()
        object EmptyEvent : SetupEvent()
    }
    
    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent
    
    private val _screen = MutableStateFlow<SetupEvent>(SetupEvent.EmptyEvent)
    val screen: StateFlow<SetupEvent> = _screen
    
    private var searchJob: Job? = null
    
    fun getArtists(artist: String)
    {
    
        _screen.value = SetupEvent.EmptyEvent
        
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatchers.main) {
            
            delay(500L)
            
            _screen.value = SetupEvent.LoadingEvent
            
            if (artist == "")
            {
                
                _screen.value = SetupEvent.GetSearchArtistsEvent(ArtistSearch(Results(Artistmatches(listOf()))))
                return@launch
            }
            
            val searchedArtists = getArtistsBySearch(artist) // Constants.CACHE_DURATION_MINUTES)
            
            if (searchedArtists is Resource.Success)
            {
                _screen.value = SetupEvent.GetSearchArtistsEvent(searchedArtists.data ?: return@launch)
            }
            else
            {
                _screen.emit(SetupEvent.EmptyEvent)
                _setupEvent.emit(SetupEvent.GetSearchArtistsErrorEvent(searchedArtists.message ?: return@launch))
            }
            
        }
        
    }
}