package com.sergey.flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sergey.flightsearch.ui.theme.FlightSearchTheme
import com.sergey.flightsearch.db.AppDatabase
import com.sergey.flightsearch.repository.FlightRepository
import com.sergey.flightsearch.ui.FlightScreen
import com.sergey.flightsearch.viewmodel.FlightViewModel
import com.sergey.flightsearch.viewmodel.FlightViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightSearchTheme {

                val database = AppDatabase.create(applicationContext)

                val repository = FlightRepository(
                    database.airportDao(),
                    database.favoriteDao()
                )

                val factory = FlightViewModelFactory(repository)

                val viewModel: FlightViewModel = viewModel(
                    factory = factory
                )

                FlightScreen(viewModel)
            }
        }
    }
}
