package com.climaconvento.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.climaconvento.R
import com.climaconvento.data.model.DayForecast
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter : ListAdapter<DayForecast, ForecastAdapter.ForecastViewHolder>(ForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast_day, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val forecastDate: TextView = itemView.findViewById(R.id.forecast_date)
        private val forecastDescription: TextView = itemView.findViewById(R.id.forecast_description)
        private val forecastTempMax: TextView = itemView.findViewById(R.id.forecast_temp_max)
        private val forecastTempMin: TextView = itemView.findViewById(R.id.forecast_temp_min)
        private val forecastWind: TextView = itemView.findViewById(R.id.forecast_wind)

        fun bind(forecast: DayForecast) {
            // Formatear fecha
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale("es", "ES"))
            
            try {
                val date = inputFormat.parse(forecast.date)
                forecastDate.text = date?.let { outputFormat.format(it) } ?: forecast.date
            } catch (e: Exception) {
                forecastDate.text = forecast.date
            }

            // Obtener descripción del primer timeframe si está disponible
            val description = forecast.timeframes?.firstOrNull()?.weatherDescription ?: ""
            forecastDescription.text = description

            // Temperaturas
            forecastTempMax.text = itemView.context.getString(
                R.string.temperature_format,
                forecast.tempMaxC
            )
            forecastTempMin.text = itemView.context.getString(
                R.string.temperature_format,
                forecast.tempMinC
            )

            // Viento
            if (forecast.windSpeedMaxMs != null) {
                forecastWind.text = itemView.context.getString(
                    R.string.wind_speed_format,
                    String.format("%.1f", forecast.windSpeedMaxMs)
                )
            } else {
                forecastWind.visibility = View.GONE
            }
        }
    }

    class ForecastDiffCallback : DiffUtil.ItemCallback<DayForecast>() {
        override fun areItemsTheSame(oldItem: DayForecast, newItem: DayForecast): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DayForecast, newItem: DayForecast): Boolean {
            return oldItem == newItem
        }
    }
}
