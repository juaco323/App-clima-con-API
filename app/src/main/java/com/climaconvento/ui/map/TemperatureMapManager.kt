package com.climaconvento.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.ViewGroup
import com.climaconvento.data.model.CurrentWeather
import com.climaconvento.data.model.Location
import com.climaconvento.utils.TemperatureColorHelper
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class TemperatureMapManager(
    private val context: Context,
    private val container: ViewGroup
) {
    private lateinit var mapView: MapView
    private var annotationManager: PointAnnotationManager? = null
    var onLocationClicked: ((Location) -> Unit)? = null
    
    fun initialize() {
        mapView = MapView(context)
        container.addView(mapView)
        
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
            setupMap()
        }
    }
    
    private fun setupMap() {
        // Centrar el mapa en Chile (región de Rancagua)
        val cameraOptions = CameraOptions.Builder()
            .center(Point.fromLngLat(-70.7406, -34.1708))
            .zoom(8.0)
            .build()
        
        mapView.getMapboxMap().setCamera(cameraOptions)
        
        // Configurar el gestor de anotaciones
        annotationManager = mapView.annotations.createPointAnnotationManager()
        
        // Agregar marcadores para ubicaciones favoritas
        addLocationMarkers()
        
        // Configurar clics en el mapa
        setupMapClickListener()
    }
    
    private fun addLocationMarkers() {
        Location.FAVORITE_LOCATIONS.forEach { location ->
            addMarker(location, null)
        }
    }
    
    private fun addMarker(location: Location, temperature: Double?) {
        val point = Point.fromLngLat(location.longitude, location.latitude)
        
        val bitmap = createTemperatureMarkerBitmap(
            temperature ?: 0.0,
            location.displayName,
            temperature != null
        )
        
        val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(point)
            .withIconImage(bitmap)
            .withTextField(location.displayName)
            .withTextOffset(listOf(0.0, 2.0))
        
        annotationManager?.create(pointAnnotationOptions)
    }
    
    private fun createTemperatureMarkerBitmap(
        temperature: Double,
        label: String,
        hasData: Boolean
    ): Bitmap {
        val size = 120
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        val paint = Paint().apply {
            isAntiAlias = true
            if (hasData) {
                color = TemperatureColorHelper.getColorForTemperature(temperature)
            } else {
                color = android.graphics.Color.GRAY
            }
            style = Paint.Style.FILL
        }
        
        // Dibujar círculo
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius * 0.8f, paint)
        
        // Dibujar borde
        paint.apply {
            color = android.graphics.Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawCircle(radius, radius, radius * 0.8f, paint)
        
        // Dibujar temperatura si hay datos
        if (hasData) {
            paint.apply {
                color = android.graphics.Color.WHITE
                style = Paint.Style.FILL
                textSize = 36f
                textAlign = Paint.Align.CENTER
            }
            val tempText = "${temperature.toInt()}°"
            canvas.drawText(tempText, radius, radius + 12f, paint)
        }
        
        return bitmap
    }
    
    fun updateTemperatureMarkers(weatherMap: Map<Location, CurrentWeather?>) {
        // Limpiar marcadores existentes
        annotationManager?.deleteAll()
        
        // Agregar marcadores actualizados
        weatherMap.forEach { (location, weather) ->
            addMarker(location, weather?.temperatureCelsius)
        }
    }
    
    private fun setupMapClickListener() {
        mapView.getMapboxMap().addOnMapClickListener { point ->
            // Buscar la ubicación más cercana al punto clickeado
            val clickedLocation = findNearestLocation(point.latitude(), point.longitude())
            clickedLocation?.let { onLocationClicked?.invoke(it) }
            true
        }
    }
    
    private fun findNearestLocation(latitude: Double, longitude: Double): Location? {
        val threshold = 0.5 // Aproximadamente 50km
        
        return Location.FAVORITE_LOCATIONS.minByOrNull { location ->
            val latDiff = location.latitude - latitude
            val lonDiff = location.longitude - longitude
            Math.sqrt(latDiff * latDiff + lonDiff * lonDiff)
        }?.takeIf { location ->
            val latDiff = location.latitude - latitude
            val lonDiff = location.longitude - longitude
            Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) < threshold
        }
    }
    
    fun centerOnLocation(location: Location) {
        val cameraOptions = CameraOptions.Builder()
            .center(Point.fromLngLat(location.longitude, location.latitude))
            .zoom(10.0)
            .build()
        
        mapView.getMapboxMap().setCamera(cameraOptions)
    }
}
