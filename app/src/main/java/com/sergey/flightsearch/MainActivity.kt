package com.sergey.flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sergey.flightsearch.ui.theme.FlightSearchTheme
import com.sergey.flightsearch.db.AppDatabase
import com.sergey.flightsearch.repository.FlightRepository
import com.sergey.flightsearch.ui.theme.FlightScreen
import com.sergey.flightsearch.viewmodel.FlightViewModel
import com.sergey.flightsearch.viewmodel.FlightViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sergey.flightsearch.datastore.SearchPreferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightSearchTheme {

                val database = AppDatabase.create(applicationContext)

                val repository = FlightRepository(
                    database.airportDao(),
                    database.favoriteDao(),
                    SearchPreferences(applicationContext)
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
