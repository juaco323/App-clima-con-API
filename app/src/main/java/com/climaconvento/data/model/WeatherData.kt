package com.climaconvento.data.model

import com.google.gson.annotations.SerializedName

// Respuesta de clima actual de OpenWeatherMap
data class CurrentWeatherResponse(
    @SerializedName("main")
    val main: MainData,
    
    @SerializedName("wind")
    val wind: WindData,
    
    @SerializedName("weather")
    val weather: List<WeatherInfo>,
    
    @SerializedName("name")
    val cityName: String?,
    
    @SerializedName("dt")
    val timestamp: Long
)

data class MainData(
    @SerializedName("temp")
    val temp: Double,
    
    @SerializedName("feels_like")
    val feelsLike: Double,
    
    @SerializedName("temp_min")
    val tempMin: Double,
    
    @SerializedName("temp_max")
    val tempMax: Double,
    
    @SerializedName("pressure")
    val pressure: Int,
    
    @SerializedName("humidity")
    val humidity: Int
)

data class WindData(
    @SerializedName("speed")
    val speed: Double,
    
    @SerializedName("deg")
    val deg: Int?
)

data class WeatherInfo(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("main")
    val main: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("icon")
    val icon: String
)

// Modelo simplificado para usar en la UI (compatible con código existente)
data class CurrentWeather(
    val temperatureCelsius: Double,
    val windSpeedMs: Double?,
    val windDirectionDeg: Int?,
    val weatherDescription: String?,
    val weatherIcon: String?,
    val humidityPercent: Int?,
    val pressureMb: Double?,
    val feelsLike: Double?
) {
    companion object {
        fun fromResponse(response: CurrentWeatherResponse): CurrentWeather {
            return CurrentWeather(
                temperatureCelsius = response.main.temp,
                windSpeedMs = response.wind.speed,
                windDirectionDeg = response.wind.deg,
                weatherDescription = response.weather.firstOrNull()?.description,
                weatherIcon = response.weather.firstOrNull()?.icon,
                humidityPercent = response.main.humidity,
                pressureMb = response.main.pressure.toDouble(),
                feelsLike = response.main.feelsLike
            )
        }
    }
}

// Respuesta de pronóstico de OpenWeatherMap
data class ForecastResponse(
    @SerializedName("list")
    val list: List<ForecastItem>,
    
    @SerializedName("city")
    val city: CityInfo
)

data class ForecastItem(
    @SerializedName("dt")
    val timestamp: Long,
    
    @SerializedName("main")
    val main: MainData,
    
    @SerializedName("wind")
    val wind: WindData,
    
    @SerializedName("weather")
    val weather: List<WeatherInfo>,
    
    @SerializedName("dt_txt")
    val dateTime: String
)

data class CityInfo(
    @SerializedName("name")
    val name: String,
    
    @SerializedName("country")
    val country: String
)

// Modelo simplificado para pronóstico diario
data class WeatherForecast(
    val date: String,
    val tempMaxC: Double,
    val tempMinC: Double,
    val windSpeedMaxMs: Double?,
    val weatherDescription: String?
)

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val days: List<DayForecast>?
) {
    companion object {
        fun fromForecastResponse(response: ForecastResponse, lat: Double, lon: Double): WeatherResponse {
            // Agrupar pronósticos por día
            val dayGroups = response.list.groupBy { item ->
                item.dateTime.substring(0, 10) // Obtener fecha (YYYY-MM-DD)
            }
            
            val days = dayGroups.map { (date, items) ->
                DayForecast(
                    date = date,
                    tempMaxC = items.maxOf { it.main.tempMax },
                    tempMinC = items.minOf { it.main.tempMin },
                    windSpeedMaxMs = items.maxOfOrNull { it.wind.speed },
                    timeframes = items.map { item ->
                        TimeframeData(
                            time = item.dateTime.substring(11, 16), // HH:MM
                            tempC = item.main.temp,
                            windSpeedMs = item.wind.speed,
                            weatherDescription = item.weather.firstOrNull()?.description
                        )
                    }
                )
            }
            
            return WeatherResponse(
                latitude = lat,
                longitude = lon,
                days = days
            )
        }
    }
}

data class DayForecast(
    val date: String,
    val tempMaxC: Double,
    val tempMinC: Double,
    val windSpeedMaxMs: Double?,
    val timeframes: List<TimeframeData>?
)

data class TimeframeData(
    val time: String,
    val tempC: Double,
    val windSpeedMs: Double?,
    val weatherDescription: String?
)
