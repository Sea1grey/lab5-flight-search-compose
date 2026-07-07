package com.sergey.flightsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergey.flightsearch.repository.FlightRepository

class FlightViewModelFactory(
    private val repository: FlightRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FlightViewModel(repository) as T
    }
}