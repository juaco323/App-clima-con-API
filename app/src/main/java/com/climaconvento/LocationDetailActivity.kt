package com.climaconvento

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.climaconvento.data.model.Location
import com.climaconvento.databinding.ActivityLocationDetailBinding
import com.climaconvento.ui.adapter.ForecastAdapter
import com.climaconvento.utils.TemperatureColorHelper
import com.climaconvento.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class LocationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationDetailBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationName = intent.getStringExtra("location_name") ?: "Convento"
        location = Location.getLocationByName(locationName) ?: Location.FAVORITE_LOCATIONS[0]

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        
        viewModel.selectLocation(location)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = location.displayName
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        forecastAdapter = ForecastAdapter()
        binding.forecastRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@LocationDetailActivity)
            adapter = forecastAdapter
        }
    }

    private fun setupObservers() {
        // Observar clima actual
        viewModel.currentWeather.observe(this) { weather ->
            if (weather != null) {
                binding.locationName.text = location.displayName
                binding.currentTemperature.text = getString(
                    R.string.temperature_format,
                    weather.temperatureCelsius
                )
                
                if (weather.windSpeedMs != null) {
                    binding.currentWind.text = getString(
                        R.string.wind_speed_format,
                        String.format("%.1f", weather.windSpeedMs)
                    )
                } else {
                    binding.currentWind.text = "N/D"
                }
                
                // Descripción de temperatura
                binding.temperatureDescription.text = 
                    TemperatureColorHelper.getTemperatureDescription(weather.temperatureCelsius)
                
                // Última actualización
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                binding.lastUpdated.text = getString(
                    R.string.last_updated,
                    dateFormat.format(Date())
                )
                
                // Cambiar color del card según temperatura
                val color = TemperatureColorHelper.getColorForTemperature(weather.temperatureCelsius)
                binding.currentWeatherCard.setCardBackgroundColor(color)
            }
        }

        // Observar pronóstico
        viewModel.forecast.observe(this) { forecast ->
            if (forecast != null && forecast.days != null) {
                forecastAdapter.submitList(forecast.days)
                binding.forecastCard.visibility = View.VISIBLE
            } else {
                binding.forecastCard.visibility = View.GONE
            }
        }

        // Observar estado de carga
        viewModel.isLoading.observe(this) { isLoading ->
            // Puedes agregar un indicador de carga si lo deseas
        }
    }
}
