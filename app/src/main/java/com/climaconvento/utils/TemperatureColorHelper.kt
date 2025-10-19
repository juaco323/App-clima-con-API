package com.climaconvento.utils

import android.graphics.Color

object TemperatureColorHelper {
    
    /**
     * Obtiene el color basado en la temperatura
     * Azul para bajas, amarillo para medias, rojo para altas
     */
    fun getColorForTemperature(temperature: Double): Int {
        return when {
            temperature <= 4.0 -> Color.rgb(33, 150, 243)    // Azul brillante
            temperature <= 10.0 -> Color.rgb(100, 181, 246)  // Azul claro
            temperature <= 15.0 -> Color.rgb(129, 212, 250)  // Azul muy claro
            temperature <= 20.0 -> Color.rgb(255, 245, 157)  // Amarillo claro
            temperature <= 25.0 -> Color.rgb(255, 213, 79)   // Amarillo
            temperature <= 30.0 -> Color.rgb(255, 152, 0)    // Naranja
            else -> Color.rgb(244, 67, 54)                    // Rojo
        }
    }
    
    /**
     * Obtiene un color hexadecimal basado en la temperatura
     */
    fun getHexColorForTemperature(temperature: Double): String {
        val color = getColorForTemperature(temperature)
        return String.format("#%06X", 0xFFFFFF and color)
    }
    
    /**
     * Obtiene una descripción de la temperatura
     */
    fun getTemperatureDescription(temperature: Double): String {
        return when {
            temperature <= 4.0 -> "Muy frío"
            temperature <= 10.0 -> "Frío"
            temperature <= 15.0 -> "Fresco"
            temperature <= 20.0 -> "Templado"
            temperature <= 25.0 -> "Cálido"
            temperature <= 30.0 -> "Caluroso"
            else -> "Muy caluroso"
        }
    }
}
