package com.climaconvento.data.local

import androidx.room.TypeConverter

class Converters {
    
    @TypeConverter
    fun fromAlertType(value: WeatherAlert.AlertType): String {
        return value.name
    }
    
    @TypeConverter
    fun toAlertType(value: String): WeatherAlert.AlertType {
        return WeatherAlert.AlertType.valueOf(value)
    }
}
