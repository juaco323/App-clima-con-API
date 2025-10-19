package com.climaconvento.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.climaconvento.data.local.AppDatabase
import com.climaconvento.data.local.WeatherAlert
import com.climaconvento.data.model.CurrentWeather
import com.climaconvento.data.model.Location
import com.climaconvento.data.model.WeatherResponse
import com.climaconvento.data.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = WeatherRepository()
    private val database = AppDatabase.getDatabase(application)
    
    private val _currentWeather = MutableLiveData<CurrentWeather?>()
    val currentWeather: LiveData<CurrentWeather?> = _currentWeather
    
    private val _forecast = MutableLiveData<WeatherResponse?>()
    val forecast: LiveData<WeatherResponse?> = _forecast
    
    private val _selectedLocation = MutableLiveData<Location>()
    val selectedLocation: LiveData<Location> = _selectedLocation
    
    private val _allLocationsWeather = MutableLiveData<Map<Location, CurrentWeather?>>()
    val allLocationsWeather: LiveData<Map<Location, CurrentWeather?>> = _allLocationsWeather
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    val alerts: Flow<List<WeatherAlert>> = database.weatherAlertDao().getAllAlerts()
    val unreadAlerts: Flow<List<WeatherAlert>> = database.weatherAlertDao().getUnreadAlerts()
    
    init {
        // Cargar todos los climas
        loadAllLocationsWeather()
        
        // Seleccionar ubicación inicial (Convento)
        if (Location.FAVORITE_LOCATIONS.isNotEmpty()) {
            selectLocation(Location.FAVORITE_LOCATIONS[0])
        }
    }
    
    fun selectLocation(location: Location) {
        _selectedLocation.value = location
        loadCurrentWeather(location)
        loadForecast(location)
    }
    
    fun loadCurrentWeather(location: Location) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = repository.getCurrentWeather(location)
            result.onSuccess { weather ->
                _currentWeather.value = weather
            }.onFailure { error ->
                _errorMessage.value = error.message
            }
            
            _isLoading.value = false
        }
    }
    
    fun loadForecast(location: Location) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = repository.getForecast(location)
            result.onSuccess { forecast ->
                _forecast.value = forecast
            }.onFailure { error ->
                _errorMessage.value = error.message
            }
            
            _isLoading.value = false
        }
    }
    
    fun loadAllLocationsWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            val weatherMap = repository.getAllLocationsWeather()
            _allLocationsWeather.value = weatherMap
            _isLoading.value = false
        }
    }
    
    fun refreshCurrentLocation() {
        _selectedLocation.value?.let { location ->
            loadCurrentWeather(location)
            loadForecast(location)
        }
    }
    
    fun markAlertAsRead(alert: WeatherAlert) {
        viewModelScope.launch {
            database.weatherAlertDao().updateAlert(alert.copy(isRead = true))
        }
    }
    
    fun deleteAlert(alert: WeatherAlert) {
        viewModelScope.launch {
            database.weatherAlertDao().deleteAlert(alert.id)
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
