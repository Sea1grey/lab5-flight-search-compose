package com.sergey.flightsearch.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sergey.flightsearch.viewmodel.FlightViewModel
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Favorite

@Composable
fun FlightScreen(
    viewModel: FlightViewModel
) {
    var searchText by remember {
        mutableStateOf("")
    }

    var airportSelected by remember {
        mutableStateOf(false)
    }

    val airports by viewModel.airports.collectAsStateWithLifecycle()
    val flights by viewModel.flights.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                airportSelected = false
                viewModel.search(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Airport")
            }
        )

        LazyColumn {

            items(airports) { airport ->

                Text(
                    text = "${airport.iata_code} - ${airport.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clickable {
                            airportSelected = true
                            viewModel.loadFlights(airport.iata_code)
                        }
                )
            }

        }

        if (flights.isNotEmpty()) {

            Text(
                text = "Flights",
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn {

                items(flights) { flight ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "${flight.departure.iata_code} → ${flight.destination.iata_code}"
                        )

                        IconButton(
                            onClick = {
                                viewModel.toggleFavorite(flight)
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (flight.isFavorite)
                                        Icons.Default.Favorite
                                    else
                                        Icons.Default.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    }

                }

            }
        }

    }
}