package com.climaconvento

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.climaconvento.data.model.Location
import com.climaconvento.databinding.ActivityMainBinding
import com.climaconvento.ui.map.TemperatureMapManager
import com.climaconvento.utils.NetworkUtils
import com.climaconvento.utils.TemperatureColorHelper
import com.climaconvento.viewmodel.WeatherViewModel
import com.climaconvento.worker.WeatherMonitorWorker
import com.google.android.material.navigation.NavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mapManager: TemperatureMapManager
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Verificar conexión a internet
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupDrawer()
        setupMap()
        setupObservers()
        setupWorkManager()
        
        // Verificar si debe abrir notificaciones
        if (intent.getBooleanExtra("show_notifications", false)) {
            openNotifications()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupDrawer() {
        drawerLayout = binding.drawerLayout
        
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.menu_open, R.string.menu_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setupMap() {
        mapManager = TemperatureMapManager(this, binding.mapContainer)
        mapManager.initialize()
        
        mapManager.onLocationClicked = { location ->
            viewModel.selectLocation(location)
            binding.weatherInfoCard.visibility = View.VISIBLE
        }
    }

    private fun setupObservers() {
        // Observar ubicación seleccionada
        viewModel.selectedLocation.observe(this) { location ->
            binding.locationName.text = location.displayName
            supportActionBar?.title = location.displayName
        }

        // Observar clima actual
        viewModel.currentWeather.observe(this) { weather ->
            if (weather != null) {
                binding.temperatureText.text = getString(
                    R.string.temperature_format,
                    weather.temperatureCelsius
                )
                
                if (weather.windSpeedMs != null) {
                    binding.windSpeedText.text = getString(
                        R.string.wind_speed_format,
                        String.format("%.1f", weather.windSpeedMs)
                    )
                } else {
                    binding.windSpeedText.text = "N/D"
                }
                
                // Actualizar color de fondo según temperatura
                val color = TemperatureColorHelper.getColorForTemperature(weather.temperatureCelsius)
                binding.weatherInfoCard.setCardBackgroundColor(color)
            }
        }

        // Observar todos los climas de ubicaciones
        viewModel.allLocationsWeather.observe(this) { weatherMap ->
            mapManager.updateTemperatureMarkers(weatherMap)
        }

        // Observar estado de carga
        viewModel.isLoading.observe(this) { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observar errores
        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }

        // Botón de ver detalles
        binding.viewDetailsButton.setOnClickListener {
            viewModel.selectedLocation.value?.let { location ->
                val intent = Intent(this, LocationDetailActivity::class.java)
                intent.putExtra("location_name", location.name)
                startActivity(intent)
            }
        }
    }

    private fun setupWorkManager() {
        // Configurar trabajo periódico para monitoreo de temperatura
        val workRequest = PeriodicWorkRequestBuilder<WeatherMonitorWorker>(
            1, TimeUnit.HOURS // Ejecutar cada hora
        ).build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notifications -> openNotifications()
            R.id.nav_refresh -> {
                if (NetworkUtils.isNetworkAvailable(this)) {
                    viewModel.loadAllLocationsWeather()
                    viewModel.refreshCurrentLocation()
                    Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show()
                } else {
                    showNoInternetDialog()
                }
            }
            R.id.nav_convento -> selectLocation(0)
            R.id.nav_campo_alegre -> selectLocation(1)
            R.id.nav_santo_domingo -> selectLocation(2)
            R.id.nav_rengo -> selectLocation(3)
            R.id.nav_rancagua -> selectLocation(4)
        }
        
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun selectLocation(index: Int) {
        if (index < Location.FAVORITE_LOCATIONS.size) {
            val location = Location.FAVORITE_LOCATIONS[index]
            viewModel.selectLocation(location)
            mapManager.centerOnLocation(location)
        }
    }

    private fun openNotifications() {
        val intent = Intent(this, NotificationsActivity::class.java)
        startActivity(intent)
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.no_internet_title)
            .setMessage(R.string.no_internet_message)
            .setPositiveButton(R.string.retry) { _, _ ->
                recreate()
            }
            .setNegativeButton(R.string.exit) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (NetworkUtils.isNetworkAvailable(this)) {
            viewModel.refreshCurrentLocation()
        }
    }
}
