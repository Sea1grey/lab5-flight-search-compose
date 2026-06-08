package com.sergey.flightsearch.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("""
        SELECT * FROM airport
        WHERE iata_code LIKE '%' || :query || '%'
        OR name LIKE '%' || :query || '%'
        ORDER BY passengers DESC
        LIMIT 10
    """)
    fun searchAirports(query: String): Flow<List<Airport>>

    @Query("""
        SELECT * FROM airport
        WHERE iata_code = :code
        LIMIT 1
    """)
    suspend fun getAirportByCode(code: String): Airport?
}