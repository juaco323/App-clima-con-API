package com.climaconvento.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.climaconvento.R
import com.climaconvento.data.local.WeatherAlert
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class WeatherAlertAdapter(
    private val onMarkRead: (WeatherAlert) -> Unit,
    private val onDelete: (WeatherAlert) -> Unit
) : ListAdapter<WeatherAlert, WeatherAlertAdapter.AlertViewHolder>(AlertDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_alert, parent, false)
        return AlertViewHolder(view, onMarkRead, onDelete)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlertViewHolder(
        itemView: View,
        private val onMarkRead: (WeatherAlert) -> Unit,
        private val onDelete: (WeatherAlert) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val alertIcon: ImageView = itemView.findViewById(R.id.alert_icon)
        private val alertTitle: TextView = itemView.findViewById(R.id.alert_title)
        private val alertDescription: TextView = itemView.findViewById(R.id.alert_description)
        private val alertTimestamp: TextView = itemView.findViewById(R.id.alert_timestamp)
        private val markReadButton: MaterialButton = itemView.findViewById(R.id.mark_read_button)
        private val deleteButton: MaterialButton = itemView.findViewById(R.id.delete_button)

        fun bind(alert: WeatherAlert) {
            alertTitle.text = alert.getTitle()
            alertDescription.text = alert.getDescription()
            
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            alertTimestamp.text = dateFormat.format(Date(alert.timestamp))

            // Cambiar color del icono según el tipo de alerta
            val iconColor = when (alert.alertType) {
                WeatherAlert.AlertType.HIGH_TEMPERATURE -> 
                    itemView.context.getColor(R.color.alert_high)
                WeatherAlert.AlertType.LOW_TEMPERATURE -> 
                    itemView.context.getColor(R.color.alert_low)
            }
            alertIcon.setColorFilter(iconColor)

            // Mostrar/ocultar botón de marcar como leída
            if (alert.isRead) {
                markReadButton.visibility = View.GONE
            } else {
                markReadButton.visibility = View.VISIBLE
                markReadButton.setOnClickListener { onMarkRead(alert) }
            }

            deleteButton.setOnClickListener { onDelete(alert) }

            // Cambiar opacidad si ya fue leída
            itemView.alpha = if (alert.isRead) 0.6f else 1.0f
        }
    }

    class AlertDiffCallback : DiffUtil.ItemCallback<WeatherAlert>() {
        override fun areItemsTheSame(oldItem: WeatherAlert, newItem: WeatherAlert): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherAlert, newItem: WeatherAlert): Boolean {
            return oldItem == newItem
        }
    }
}
