package com.sergey.flightsearch.repository

import com.sergey.flightsearch.dao.AirportDao
import com.sergey.flightsearch.dao.FavoriteDao
import com.sergey.flightsearch.entity.Airport
import com.sergey.flightsearch.entity.Favorite
import kotlinx.coroutines.flow.Flow
import com.sergey.flightsearch.model.Flight
import com.sergey.flightsearch.datastore.SearchPreferences

class FlightRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
    private val searchPreferences: SearchPreferences
) {

    fun searchAirports(query: String): Flow<List<Airport>> {
        return airportDao.searchAirports(query)
    }

    suspend fun getAirportByCode(code: String): Airport? {
        return airportDao.getAirportByCode(code)
    }

    fun getFavorites(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    suspend fun addFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    suspend fun getFlights(departureCode: String): List<Flight> {

        val departure = airportDao.getAirportByCode(departureCode)
            ?: return emptyList()

        val destinations = airportDao.getDestinations(departureCode)

        return destinations.map { destination ->

            val isFavorite = favoriteDao.isFavorite(
                departure.iata_code,
                destination.iata_code
            ) > 0

            Flight(
                departure = departure,
                destination = destination,
                isFavorite = isFavorite
            )
        }
    }

    suspend fun isFavorite(
        departure: String,
        destination: String
    ): Boolean {
        return favoriteDao.isFavorite(departure, destination) > 0
    }

    suspend fun removeFavorite(
        departure: String,
        destination: String
    ) {
        favoriteDao.deleteFavorite(departure, destination)
    }

    suspend fun saveSearch(query: String) {
        searchPreferences.saveSearch(query)
    }

    fun getSavedSearch(): Flow<String> {
        return searchPreferences.searchQuery
    }
}