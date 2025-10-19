package com.climaconvento.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.climaconvento.MainActivity
import com.climaconvento.R
import com.climaconvento.data.local.WeatherAlert

class NotificationHelper(private val context: Context) {
    
    companion object {
        private const val CHANNEL_ID = "weather_alerts_channel"
        private const val CHANNEL_NAME = "Alertas de Clima"
        private const val CHANNEL_DESCRIPTION = "Notificaciones de temperaturas altas y bajas"
        private const val NOTIFICATION_ID_BASE = 1000
    }
    
    init {
        createNotificationChannel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                setShowBadge(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    fun showWeatherAlert(alert: WeatherAlert) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("show_notifications", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather_alert)
            .setContentTitle(alert.getTitle())
            .setContentText(alert.getDescription())
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.getDescription()))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        try {
            NotificationManagerCompat.from(context).notify(
                NOTIFICATION_ID_BASE + alert.id.toInt(),
                notification
            )
        } catch (e: SecurityException) {
            // Permisos de notificación no otorgados
            e.printStackTrace()
        }
    }
    
    fun cancelNotification(alertId: Long) {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID_BASE + alertId.toInt())
    }
}
