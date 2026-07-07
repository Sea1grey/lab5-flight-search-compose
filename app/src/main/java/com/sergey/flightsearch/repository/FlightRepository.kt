package com.sergey.flightsearch.repository

import com.sergey.flightsearch.dao.AirportDao
import com.sergey.flightsearch.dao.FavoriteDao
import com.sergey.flightsearch.entity.Airport
import com.sergey.flightsearch.entity.Favorite
import kotlinx.coroutines.flow.Flow

class FlightRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao
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

    suspend fun removeFavorite(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }
}