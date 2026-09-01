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
import com.sergey.flightsearch.model.Flight
import kotlinx.coroutines.launch
import com.sergey.flightsearch.entity.Favorite

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

    fun saveSearch(query: String) {
        viewModelScope.launch {
            repository.saveSearch(query)
        }
    }

    private val _flights = MutableStateFlow<List<Flight>>(emptyList())
    val flights: StateFlow<List<Flight>> = _flights.asStateFlow()

    fun loadFlights(departureCode: String) {
        viewModelScope.launch {
            _flights.value = repository.getFlights(departureCode)
        }
    }

    fun toggleFavorite(flight: Flight) {
        viewModelScope.launch {

            if (flight.isFavorite) {
                repository.removeFavorite(
                    flight.departure.iata_code,
                    flight.destination.iata_code
                )
            } else {
                repository.addFavorite(
                    Favorite(
                        departure_code = flight.departure.iata_code,
                        destination_code = flight.destination.iata_code
                    )
                )
            }

            loadFlights(flight.departure.iata_code)
        }
    }

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites.asStateFlow()
    private val _savedSearch = MutableStateFlow("")
    val savedSearch: StateFlow<String> = _savedSearch.asStateFlow()

    init {
        repository.getFavorites()
            .onEach {
                _favorites.value = it
            }
            .launchIn(viewModelScope)
        repository.getSavedSearch()
            .onEach { query ->
                _savedSearch.value = query

                if (query.isNotBlank()) {
                    search(query)
                    loadFlights(query)
                }
            }
            .launchIn(viewModelScope)
    }
}