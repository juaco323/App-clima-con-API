package com.climaconvento.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.climaconvento.data.local.AppDatabase
import com.climaconvento.data.local.WeatherAlert
import com.climaconvento.data.model.Location
import com.climaconvento.data.repository.WeatherRepository
import com.climaconvento.notifications.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherMonitorWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    private val repository = WeatherRepository()
    private val database = AppDatabase.getDatabase(context)
    private val notificationHelper = NotificationHelper(context)
    
    companion object {
        const val HIGH_TEMP_THRESHOLD = 25.0  // °C
        const val LOW_TEMP_THRESHOLD = 4.0    // °C
        const val ALERT_COOLDOWN_HOURS = 6    // Horas entre alertas similares
    }
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Eliminar alertas expiradas (más de 24 horas)
            val twentyFourHoursAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
            database.weatherAlertDao().deleteExpiredAlerts(twentyFourHoursAgo)
            
            // Verificar temperatura de cada ubicación
            for (location in Location.FAVORITE_LOCATIONS) {
                val weatherResult = repository.getCurrentWeather(location)
                
                weatherResult.onSuccess { weather ->
                    checkAndCreateAlert(location, weather.temperatureCelsius)
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
    
    private suspend fun checkAndCreateAlert(location: Location, temperature: Double) {
        val alertType = when {
            temperature >= HIGH_TEMP_THRESHOLD -> WeatherAlert.AlertType.HIGH_TEMPERATURE
            temperature <= LOW_TEMP_THRESHOLD -> WeatherAlert.AlertType.LOW_TEMPERATURE
            else -> return // No se necesita alerta
        }
        
        // Verificar si ya existe una alerta similar reciente
        val cooldownTime = System.currentTimeMillis() - (ALERT_COOLDOWN_HOURS * 60 * 60 * 1000)
        val existingAlert = database.weatherAlertDao().findSimilarAlert(
            location.displayName,
            temperature,
            alertType,
            cooldownTime
        )
        
        if (existingAlert == null) {
            // Crear nueva alerta
            val alert = WeatherAlert(
                locationName = location.displayName,
                temperature = temperature,
                alertType = alertType,
                timestamp = System.currentTimeMillis(),
                isRead = false
            )
            
            val alertId = database.weatherAlertDao().insertAlert(alert)
            
            // Mostrar notificación
            notificationHelper.showWeatherAlert(alert.copy(id = alertId))
        }
    }
}
