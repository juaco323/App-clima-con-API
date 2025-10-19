package com.climaconvento

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.climaconvento.databinding.ActivityNotificationsBinding
import com.climaconvento.ui.adapter.WeatherAlertAdapter
import com.climaconvento.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var alertAdapter: WeatherAlertAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeAlerts()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.notifications)
        
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        alertAdapter = WeatherAlertAdapter(
            onMarkRead = { alert ->
                viewModel.markAlertAsRead(alert)
            },
            onDelete = { alert ->
                viewModel.deleteAlert(alert)
            }
        )

        binding.alertsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationsActivity)
            adapter = alertAdapter
        }
    }

    private fun observeAlerts() {
        lifecycleScope.launch {
            viewModel.alerts.collect { alerts ->
                if (alerts.isEmpty()) {
                    binding.emptyState.visibility = View.VISIBLE
                    binding.alertsRecyclerView.visibility = View.GONE
                } else {
                    binding.emptyState.visibility = View.GONE
                    binding.alertsRecyclerView.visibility = View.VISIBLE
                    alertAdapter.submitList(alerts)
                }
            }
        }
    }
}
