package com.climaconvento.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherAlertDao {
    
    @Query("SELECT * FROM weather_alerts ORDER BY timestamp DESC")
    fun getAllAlerts(): Flow<List<WeatherAlert>>
    
    @Query("SELECT * FROM weather_alerts WHERE isRead = 0 ORDER BY timestamp DESC")
    fun getUnreadAlerts(): Flow<List<WeatherAlert>>
    
    @Insert
    suspend fun insertAlert(alert: WeatherAlert): Long
    
    @Update
    suspend fun updateAlert(alert: WeatherAlert)
    
    @Query("DELETE FROM weather_alerts WHERE id = :alertId")
    suspend fun deleteAlert(alertId: Long)
    
    @Query("DELETE FROM weather_alerts WHERE timestamp < :expirationTime")
    suspend fun deleteExpiredAlerts(expirationTime: Long)
    
    @Query("SELECT * FROM weather_alerts WHERE locationName = :locationName AND temperature = :temperature AND alertType = :alertType AND timestamp > :minTimestamp LIMIT 1")
    suspend fun findSimilarAlert(
        locationName: String,
        temperature: Double,
        alertType: WeatherAlert.AlertType,
        minTimestamp: Long
    ): WeatherAlert?
}
