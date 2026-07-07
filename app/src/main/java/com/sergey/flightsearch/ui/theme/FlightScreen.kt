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

@Composable
fun FlightScreen(
    viewModel: FlightViewModel
) {
    var searchText by remember {
        mutableStateOf("")
    }

    val airports by viewModel.airports.collectAsStateWithLifecycle()

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
                    modifier = Modifier.padding(12.dp)
                )
            }

        }

    }
}