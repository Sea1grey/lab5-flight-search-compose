package com.sergey.flightsearch.model

import com.sergey.flightsearch.entity.Airport

data class Flight(
    val departure: Airport,
    val destination: Airport,
    val isFavorite: Boolean
)