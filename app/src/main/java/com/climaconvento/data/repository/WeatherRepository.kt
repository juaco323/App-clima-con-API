package com.climaconvento.data.repository

import com.climaconvento.data.api.RetrofitClient
import com.climaconvento.data.model.CurrentWeather
import com.climaconvento.data.model.Location
import com.climaconvento.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {
    
    private val apiService = RetrofitClient.weatherApiService
    
    /**
     * Obtiene el clima actual para una ubicación
     */
    suspend fun getCurrentWeather(location: Location): Result<CurrentWeather> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCurrentWeather(
                    lat = location.latitude,
                    lon = location.longitude
                )
                
                if (response.isSuccessful && response.body() != null) {
                    val currentWeather = CurrentWeather.fromResponse(response.body()!!)
                    Result.success(currentWeather)
                } else {
                    Result.failure(Exception("Error al obtener el clima: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}", e))
            }
        }
    }
    
    /**
     * Obtiene el pronóstico del tiempo para una ubicación
     */
    suspend fun getForecast(location: Location): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getForecast(
                    lat = location.latitude,
                    lon = location.longitude
                )
                
                if (response.isSuccessful && response.body() != null) {
                    val weatherResponse = WeatherResponse.fromForecastResponse(
                        response.body()!!,
                        location.latitude,
                        location.longitude
                    )
                    Result.success(weatherResponse)
                } else {
                    Result.failure(Exception("Error al obtener el pronóstico: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error de conexión: ${e.message}", e))
            }
        }
    }
    
    /**
     * Obtiene el clima para todas las ubicaciones favoritas
     */
    suspend fun getAllLocationsWeather(): Map<Location, CurrentWeather?> {
        return withContext(Dispatchers.IO) {
            val weatherMap = mutableMapOf<Location, CurrentWeather?>()
            
            for (location in Location.FAVORITE_LOCATIONS) {
                val result = getCurrentWeather(location)
                weatherMap[location] = result.getOrNull()
            }
            
            weatherMap
        }
    }
}
