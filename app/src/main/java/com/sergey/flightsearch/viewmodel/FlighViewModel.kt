package com.sergey.flightsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergey.flightsearch.entity.Airport
import com.sergey.flightsearch.repository.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.Job

class FlightViewModel(
    private val repository: FlightRepository
) : ViewModel() {

    private val _airports = MutableStateFlow<List<Airport>>(emptyList())
    val airports: StateFlow<List<Airport>> = _airports.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) {
            _airports.value = emptyList()
            return
        }

        repository.searchAirports(query)
            .onEach { airports ->
                _airports.value = airports
            }
            .launchIn(viewModelScope)
    }
}