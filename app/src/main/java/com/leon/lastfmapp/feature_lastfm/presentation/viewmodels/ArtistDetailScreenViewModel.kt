package com.leon.lastfmapp.feature_lastfm.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.lastfmapp.common.util.DispatcherProvider
import com.leon.lastfmapp.common.util.Resource
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
    // private val getCityCoordinates: GetCityCoordinates,
    // private val getWeatherDetails: GetWeatherDetails,
    private val dispatchers: DispatcherProvider,
    // CACHING
    // private val dao: WeatherDetailsDao
) : ViewModel()
{
    
    sealed class SetupEvent
    {
        
        // data class GetCityWeatherDetailsEvent(val weatherDetails: WeatherDetails, val city: String) : SetupEvent()
        data class GetCityWeatherDetailsErrorEvent(val error: String) : SetupEvent()
        
        object LoadingEvent : SetupEvent()
        object EmptyEvent : SetupEvent()
    }
    
    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent
    
    private val _screen = MutableStateFlow<SetupEvent>(SetupEvent.EmptyEvent)
    val screen: StateFlow<SetupEvent> = _screen
    
    fun getWeatherDetailsForCity(city: String)
    {
        _screen.value = SetupEvent.LoadingEvent
        
        viewModelScope.launch(dispatchers.main) {
            
            /*
            val cityCoords = getCityCoordinates(city, Constants.CACHE_DURATION_MINUTES)
            
            if (cityCoords is Resource.Success)
            {
                _screen.value = SetupEvent.GetCityWeatherDetailsEvent(cityWeatherDetails.data ?: return@launch, city)
            }
            else
            {
                _screen.emit(SetupEvent.MainScreenEmptyEvent)
                _setupEvent.emit(SetupEvent.GetCityWeatherDetailsErrorEvent(cityWeatherDetails.message ?: return@launch))
            }
            */
            
        }
    }
}