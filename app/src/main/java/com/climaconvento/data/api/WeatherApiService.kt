package com.climaconvento.data.api

import com.climaconvento.data.model.CurrentWeatherResponse
import com.climaconvento.data.model.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val API_KEY = "26081eb68476c542fc98cb1dd9d7da34"
    }
    
    /**
     * Obtiene el clima actual para una ubicación específica
     * @param lat Latitud (ej: -33.4489)
     * @param lon Longitud (ej: -70.6693)
     */
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "es"
    ): Response<CurrentWeatherResponse>
    
    /**
     * Obtiene el pronóstico del tiempo para una ubicación específica (5 días, cada 3 horas)
     * @param lat Latitud (ej: -33.4489)
     * @param lon Longitud (ej: -70.6693)
     */
    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "es"
    ): Response<ForecastResponse>
}
