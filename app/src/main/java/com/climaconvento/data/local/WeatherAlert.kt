package com.climaconvento.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_alerts")
data class WeatherAlert(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val locationName: String,
    val temperature: Double,
    val alertType: AlertType,
    val timestamp: Long,
    val isRead: Boolean = false
) {
    enum class AlertType {
        HIGH_TEMPERATURE,  // >= 25°C
        LOW_TEMPERATURE    // <= 4°C
    }
    
    fun getTitle(): String {
        return when (alertType) {
            AlertType.HIGH_TEMPERATURE -> "ALERTA: Temperatura alta"
            AlertType.LOW_TEMPERATURE -> "ALERTA: Temperatura baja"
        }
    }
    
    fun getDescription(): String {
        return "La temperatura actual registrada en $locationName es de: ${String.format("%.1f", temperature)}°Celsius"
    }
    
    // Verifica si la alerta ha expirado (más de 24 horas)
    fun isExpired(): Boolean {
        val twentyFourHoursInMillis = 24 * 60 * 60 * 1000
        return System.currentTimeMillis() - timestamp > twentyFourHoursInMillis
    }
}
