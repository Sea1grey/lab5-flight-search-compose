package com.sergey.flightsearch.ui

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
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun FlightScreen(
    viewModel: FlightViewModel
) {
    val savedSearch by viewModel.savedSearch.collectAsStateWithLifecycle()

    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(savedSearch) {
        if (savedSearch.isNotBlank() && searchText.isBlank()) {
            searchText = savedSearch
        }
    }

    val airports by viewModel.airports.collectAsStateWithLifecycle()
    val flights by viewModel.flights.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
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
                            viewModel.loadFlights(airport.iata_code)
                            viewModel.saveSearch(airport.iata_code)
                        }
                )
            }

        }

        if (searchText.isBlank()) {

            LazyColumn {
                items(favorites) { favorite ->

                    Text(
                        text = "${favorite.departure_code} → ${favorite.destination_code}",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

        } else {

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